package com.meli.weather.service.impl;

import com.meli.weather.crosscutting.constants.ConstantParam;
import com.meli.weather.crosscutting.constants.ConstantsEmail;
import com.meli.weather.crosscutting.mapper.WeatherMapper;
import com.meli.weather.crosscutting.util.ResponseCodes;
import com.meli.weather.dataprovider.jpa.entity.BuyerNotificationEntity;
import com.meli.weather.dataprovider.jpa.repository.IBuyerNotificationRepository;
import com.meli.weather.dataprovider.rest.IWeatherApi;
import com.meli.weather.exception.MeliWeatherException;
import com.meli.weather.model.BuyerNotificationRsDto;
import com.meli.weather.model.ForecastDayRsDto;
import com.meli.weather.model.WeatherApiRsDto;
import com.meli.weather.model.WeatherRsDto;
import com.meli.weather.service.IWeatherService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements IWeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final IWeatherApi weatherApi;
    private final IBuyerNotificationRepository buyerNotificationRepository;

    @Override
    public WeatherApiRsDto getWeatherApi(Map<String, Object> requestParam) {
        ResponseEntity<WeatherApiRsDto> response;
        try {
            response = weatherApi.weatherApiRsDto(requestParam);
            LOG.info("WeatherServiceImpl :: getWeatherApi");
        } catch (Exception e) {
            throw new MeliWeatherException(ResponseCodes.MELI_BUYER_CONSUMING_API);
        }
        return response.getBody();
    }

    @Override
    public WeatherRsDto getWeather(Map<String, Object> requestParam, String email) {
        WeatherRsDto response;
        LOG.info("WeatherServiceImpl :: getWeather");
        try {
            WeatherApiRsDto getWeatherApi = getWeatherApi(requestParam);

            LocalDate localDate = LocalDate.now().plusDays(1);

            ForecastDayRsDto forecastDay = getWeatherApi.getForecast().getForecastDay().stream().filter(date -> date.getDate().equals(localDate.toString())).findFirst().orElseThrow(() -> new RuntimeException("NO"));

            Boolean buyerNotification = getBuyerNotification(forecastDay.getDay().getCondition().getCode());

            if (buyerNotification) {
                sendEmail(email, forecastDay.getDay().getCondition().getText());
                BuyerNotificationEntity buyerNotificationEntity = BuyerNotificationEntity.builder()
                        .email(email)
                        .notification(LocalDateTime.now())
                        .deliveryLocation(requestParam.get(ConstantParam.Q).toString())
                        .forecastCode(Integer.parseInt(forecastDay.getDay().getCondition().getCode()))
                        .forecast(forecastDay.getDay().getCondition().getText())
                        .build();

                buyerNotificationRepository.save(buyerNotificationEntity);

            }

            response = WeatherRsDto.builder()
                    .forecastCode(Integer.valueOf(forecastDay.getDay().getCondition().getCode()))
                    .forecastDescription(forecastDay.getDay().getCondition().getText())
                    .buyerNotification(getBuyerNotification(forecastDay.getDay().getCondition().getCode()))
                    .build();

        } catch (MeliWeatherException exception) {
            throw new MeliWeatherException(exception.getCodes());
        } catch (Exception exception) {
            throw new MeliWeatherException(ResponseCodes.MELI_INTERNAL_500);
        }
        return response;
    }

    @Override
    public List<BuyerNotificationRsDto> getNotifications(String email) {
        LOG.info("WeatherServiceImpl :: getNotifications");
        ArrayList<BuyerNotificationRsDto> buyerList = new ArrayList<>();
        Optional<List<BuyerNotificationEntity>> buyerNotificationEntityList = buyerNotificationRepository.findByEmail(email);

        if (buyerNotificationEntityList.get().size() != 0) {
            buyerNotificationEntityList.get().forEach(buyer ->
                    buyerList.add(BuyerNotificationRsDto.builder()
                            .notification(buyer.getNotification())
                            .deliveryLocation(buyer.getDeliveryLocation())
                            .forecast(buyer.getForecast())
                            .build()));
        } else {
            throw new MeliWeatherException(ResponseCodes.MELI_BUYER_NOTIFICATION_NOT_FOUND);
        }


        return buyerList;
    }

    @Override
    public void sendEmail(String email, String condition) {
        LOG.info("WeatherServiceImpl :: sendEmail");
        Session session = Session.getInstance(WeatherMapper.propertiesMapper(),
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ConstantsEmail.USER_EMAIL, ConstantsEmail.PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConstantsEmail.USER_EMAIL));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(ConstantsEmail.SUBJECT);
            message.setText(ConstantsEmail.TEXT.replace("{condition}", condition));

            Transport.send(message);

        } catch (MessagingException e) {
            throw new MeliWeatherException(ResponseCodes.MELI_BUYER_SEND_NOTIFICATION);
        }
    }


    public static Boolean getBuyerNotification(String code) {

        return switch (code) {
            case "1186", "1189", "1192", "1195" -> Boolean.TRUE;
            default -> Boolean.FALSE;
        };

    }
}
