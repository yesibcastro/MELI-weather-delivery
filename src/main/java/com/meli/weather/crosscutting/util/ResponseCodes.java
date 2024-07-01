package com.meli.weather.crosscutting.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCodes {

    MELI_BUYER_NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"ERROR","BUYER","400","Buyer no encontrado"),
    MELI_BUYER_SEND_NOTIFICATION(HttpStatus.BAD_REQUEST,"ERROR","BUYER","400","Error al enviar el email"),
    MELI_BUYER_CONSUMING_API(HttpStatus.BAD_REQUEST,"ERROR","BUYER","400","Error al consumir API de clima"),
    MELI_INTERNAL_500(HttpStatus.INTERNAL_SERVER_ERROR,"ERROR","BUYER","500","Internal Server Error");

    private final HttpStatus httpStatus;
    private final String errorSeverity;
    private final String sourceSystem;
    private final String code;
    private final String description;

    public String getCodeAsString(){
        return String.valueOf(this.getCode());
    }

}
