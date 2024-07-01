package com.meli.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayRsDto {

    @JsonProperty("condition")
    private ConditionRsDto condition;
}
