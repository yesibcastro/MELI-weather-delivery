package com.meli.weather.dataprovider.rest;

import com.meli.weather.crosscutting.constants.ConstantsPaths;
import com.meli.weather.model.WeatherApiRsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(url = "${services.weather.api.url}", name = "weatherapi")
public interface IWeatherApi {

    @GetMapping(value = ConstantsPaths.GET_WEATHERAPI)
    ResponseEntity<WeatherApiRsDto> weatherApiRsDto(
            @RequestParam Map<String, Object> requestParam
    );
}
