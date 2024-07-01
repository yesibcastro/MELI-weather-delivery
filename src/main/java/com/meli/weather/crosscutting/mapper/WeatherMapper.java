package com.meli.weather.crosscutting.mapper;

import com.meli.weather.crosscutting.constants.ConstantParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WeatherMapper {

    private WeatherMapper() {

    }

    public static Map<String, Object> requestParam(String key, String q, String days, String aqi, String alerts, String lang) {

        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put(ConstantParam.KEY, key);
        requestParam.put(ConstantParam.Q, q);
        requestParam.put(ConstantParam.DAYS, days);
        requestParam.put(ConstantParam.AQI, aqi);
        requestParam.put(ConstantParam.ALERTS, alerts);
        requestParam.put(ConstantParam.LANG, lang);
        return requestParam;
    }

    public static Properties propertiesMapper(){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return props;
    }

}
