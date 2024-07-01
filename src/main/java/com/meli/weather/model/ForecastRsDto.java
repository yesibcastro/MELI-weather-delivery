package com.meli.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForecastRsDto {

    @JsonProperty("forecastday")
    private List<ForecastDayRsDto> forecastDay;
}
