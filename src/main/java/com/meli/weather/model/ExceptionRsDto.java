package com.meli.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionRsDto {

    @JsonProperty("source-system")
    private String sourceSystem;

    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

}
