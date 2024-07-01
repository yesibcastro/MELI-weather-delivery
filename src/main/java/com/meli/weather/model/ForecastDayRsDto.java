package com.meli.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDayRsDto {

    @JsonProperty("date")
    private String date;

    @JsonProperty("day")
    private DayRsDto day;
}
