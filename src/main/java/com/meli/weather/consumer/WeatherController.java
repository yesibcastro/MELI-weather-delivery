package com.meli.weather.consumer;

import com.meli.weather.crosscutting.constants.ConstantParam;
import com.meli.weather.crosscutting.mapper.WeatherMapper;
import com.meli.weather.exception.MeliWeatherException;
import com.meli.weather.model.BuyerNotificationRsDto;
import com.meli.weather.model.ExceptionRsDto;
import com.meli.weather.model.WeatherRsDto;
import com.meli.weather.service.IWeatherService;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/meli")
@RequiredArgsConstructor
public class WeatherController {

    private final IWeatherService weatherService;

    @GetMapping(path = "/weather")
    public ResponseEntity<Object> getWeather(
            @NotNull @ApiParam(value = "This header is used to get the user's email.", required = true)
            @Email @Valid @RequestParam(value = "email") String email,
            @NotNull @ApiParam(value = "This parameter is used to identify the delivery location", required = true)
            @Valid @RequestParam(value = "delivery-location") String deliveryLocation
    ) {

        try{
            Map<String, Object> requestParam = WeatherMapper.requestParam(ConstantParam.KEY_VALUE, deliveryLocation, "2", "no", "no", "es");
            WeatherRsDto response = weatherService.getWeather(requestParam, email);
            return ResponseEntity.ok(response);
        }catch (MeliWeatherException exception){
            return ResponseEntity.unprocessableEntity().body(ExceptionRsDto.builder()
                    .sourceSystem(exception.getCodes().getSourceSystem())
                    .code(exception.getCodes().getCode())
                    .description(exception.getCodes().getDescription())
                    .build());
        }

    }

    @GetMapping(path = "/buyer-notification")
    public ResponseEntity<Object> getBuyerNotification(
            @NotNull @ApiParam(value = "This header is used to get the user's email.", required = true)
            @Valid @RequestParam(value = "email") String email
    ) {
        try {
            List<BuyerNotificationRsDto> response = weatherService.getNotifications(email);
            return ResponseEntity.ok(response);
        } catch (MeliWeatherException exception) {
            return ResponseEntity.unprocessableEntity().body(ExceptionRsDto.builder()
                    .sourceSystem(exception.getCodes().getSourceSystem())
                    .code(exception.getCodes().getCode())
                    .description(exception.getCodes().getDescription())
                    .build());
        }

    }
}