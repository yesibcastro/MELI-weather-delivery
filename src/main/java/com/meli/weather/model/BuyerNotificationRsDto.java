package com.meli.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.meli.weather.exception.MeliWeatherException;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyerNotificationRsDto {

    @JsonProperty("notification")
    private LocalDateTime notification;

    @JsonProperty("delivery-location")
    private String deliveryLocation;

    @JsonProperty("forecast")
    private String forecast;

}
