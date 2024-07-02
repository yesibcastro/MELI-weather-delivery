package com.meli.weather.service.impl;

import com.meli.weather.DummyMock;
import com.meli.weather.crosscutting.constants.ConstantParam;
import com.meli.weather.crosscutting.mapper.WeatherMapper;
import com.meli.weather.dataprovider.jpa.entity.BuyerNotificationEntity;
import com.meli.weather.dataprovider.jpa.repository.IBuyerNotificationRepository;
import com.meli.weather.dataprovider.rest.IWeatherApi;
import com.meli.weather.exception.MeliWeatherException;
import com.meli.weather.model.BuyerNotificationRsDto;
import com.meli.weather.model.WeatherApiRsDto;
import com.meli.weather.model.WeatherRsDto;
import jakarta.mail.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceImplTest {

    @Mock
    private IWeatherApi weatherApi;
    @Mock
    private IBuyerNotificationRepository buyerNotificationRepository;
    @Mock
    private Session session;
    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    public void getWeatherApiTest(){
        Map<String, Object> requestParam = WeatherMapper.requestParam(ConstantParam.KEY_VALUE, "6.1543519,-75.6076758", "2", "no", "no", "es");

        Mockito.when(this.weatherApi.weatherApiRsDto(Mockito.anyMap())).thenReturn(DummyMock.buildWeatherApi());
        WeatherApiRsDto response = weatherService.getWeatherApi(requestParam);

        Assertions.assertNotNull(response);

    }

    @Test
    public void getBuyerNotificationTest(){

        Mockito.when(this.buyerNotificationRepository.findByEmail(Mockito.anyString())).thenReturn(DummyMock.buildBuyerNotificationEntityList());
        List<BuyerNotificationRsDto> response = weatherService.getNotifications("yesibcastro@gmail.com");
        Assertions.assertNotNull(response);
    }

    @Test
    public void getWeatherTest(){
        Map<String, Object> requestParam = WeatherMapper.requestParam(ConstantParam.KEY_VALUE, "6.1543519,-75.6076758", "2", "no", "no", "es");
        Mockito.when(this.buyerNotificationRepository.save(Mockito.any(BuyerNotificationEntity.class))).thenReturn(DummyMock.buildBuyerEntity());
        Mockito.when(this.weatherApi.weatherApiRsDto(Mockito.anyMap())).thenReturn(DummyMock.buildWeatherApi());
        WeatherRsDto response = weatherService.getWeather(requestParam,"yesibcastro@gmail.com");
        Assertions.assertNotNull(response);
    }

    @Test
    public void getWeatherExceptionTest(){
        Map<String, Object> requestParam = WeatherMapper.requestParam(ConstantParam.KEY_VALUE, "6.1543519,-75.6076758", "2", "no", "no", "es");
        MeliWeatherException exception = Assertions.assertThrows(MeliWeatherException.class, () -> weatherService.getWeather(requestParam,"yesibcastro@gmail.com"));
        Assertions.assertNotNull(exception);
    }

    @Test
    public void getBuyerNotificationExceptionTest(){

        Mockito.when(this.buyerNotificationRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new ArrayList<>()));
        MeliWeatherException exception = Assertions.assertThrows(MeliWeatherException.class, () -> weatherService.getNotifications("yesibcastro@gmail.com"));
        Assertions.assertNotNull(exception);
    }

}
