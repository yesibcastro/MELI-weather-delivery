package com.meli.weather;


import com.meli.weather.dataprovider.jpa.entity.BuyerNotificationEntity;
import com.meli.weather.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DummyMock {

    public static ResponseEntity<WeatherApiRsDto> buildWeatherApi(){
        WeatherApiRsDto response = WeatherApiRsDto.builder()
                .forecast(ForecastRsDto.builder()
                        .forecastDay(buildForecastDay())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static List<ForecastDayRsDto> buildForecastDay(){

        ForecastDayRsDto buildForecastNow = ForecastDayRsDto.builder()
                .date(LocalDate.now().toString())
                .day(DayRsDto.builder()
                        .condition(ConditionRsDto.builder()
                                .text("Lluvia moderada")
                                .code("1189")
                                .icon("Test")
                                .build())
                        .build())
                .build();

        ForecastDayRsDto buildForecastPlus = ForecastDayRsDto.builder()
                .date(LocalDate.now().plusDays(1).toString())
                .day(DayRsDto.builder()
                        .condition(ConditionRsDto.builder()
                                .text("Lluvia moderada")
                                .code("1189")
                                .icon("Test")
                                .build())
                        .build())
                .build();

        List<ForecastDayRsDto> forecastDay = new ArrayList<>();
        forecastDay.add(buildForecastNow);
        forecastDay.add(buildForecastPlus);
        return forecastDay;
    }

    public static Optional<List<BuyerNotificationEntity>> buildBuyerNotificationEntityList(){
        List<BuyerNotificationEntity> buildList = new ArrayList<>();
        buildList.add(buildBuyerEntity());

        return  Optional.of(buildList);
    }

    public static BuyerNotificationEntity buildBuyerEntity(){
        BuyerNotificationEntity buyerNotificationEntity = BuyerNotificationEntity.builder()
                .notification(LocalDateTime.now())
                .email("TEST@gmail.com")
                .deliveryLocation("6.1543519,-75.6076758")
                .forecast("TEST")
                .forecastCode(1189)
                .build();
        return buyerNotificationEntity;
    }

    public static WeatherRsDto buildWeatherRsDto(){

        WeatherRsDto response = WeatherRsDto.builder()
                .forecastCode(1189)
                .forecastDescription("TEST")
                .buyerNotification(Boolean.TRUE)
                .build();
        return response;

    }

    public static List<BuyerNotificationRsDto> buildBuyerNotificationRsDto(){

        BuyerNotificationRsDto response = BuyerNotificationRsDto.builder()
                .notification(LocalDateTime.now())
                .forecast("TEST")
                .deliveryLocation("TEST")
                .build();
       List<BuyerNotificationRsDto> responseList = new ArrayList<>();
        responseList.add(response);
        return responseList;

    }
}
