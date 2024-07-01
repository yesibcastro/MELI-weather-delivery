package com.meli.weather.exception;

import com.meli.weather.crosscutting.util.ResponseCodes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MeliWeatherException extends RuntimeException {

    private final ResponseCodes codes;

    public MeliWeatherException(ResponseCodes codes) {
        this.codes = codes;
    }


    @Override
    public String getMessage() {
        return this.codes.getDescription();
    }

    public HttpStatus getHttpStatus() {
        return this.codes.getHttpStatus();
    }
}
