package com.meli.weather.service;

import com.meli.weather.model.BuyerNotificationRsDto;
import com.meli.weather.model.WeatherApiRsDto;
import com.meli.weather.model.WeatherRsDto;

import java.util.List;
import java.util.Map;

public interface IWeatherService {

    WeatherApiRsDto getWeatherApi( Map<String, Object> requestParam);

    WeatherRsDto getWeather(Map<String, Object> requestParam, String email);

    List<BuyerNotificationRsDto> getNotifications(String email);

    void sendEmail(String email, String condition);
}
