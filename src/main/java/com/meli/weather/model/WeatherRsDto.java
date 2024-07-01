package com.meli.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRsDto {

    @JsonProperty("forecast_code")
    private Integer forecastCode;

    @JsonProperty("forecast_description")
    private String forecastDescription;

    @JsonProperty("buyer_notification")
    private Boolean buyerNotification;
}
