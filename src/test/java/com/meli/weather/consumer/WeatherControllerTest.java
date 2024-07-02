package com.meli.weather.consumer;

import com.meli.weather.DummyMock;
import com.meli.weather.crosscutting.constants.ConstantParam;
import com.meli.weather.crosscutting.mapper.WeatherMapper;
import com.meli.weather.crosscutting.util.ResponseCodes;
import com.meli.weather.exception.MeliWeatherException;
import com.meli.weather.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherServiceImpl weatherServiceImpl;


    @Test
    public void getWeatherTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(weatherController).alwaysDo(print()).build();
        Map<String, Object> requestParam = WeatherMapper.requestParam(ConstantParam.KEY_VALUE, "6.1543519,-75.6076758", "2", "no", "no", "es");
        Mockito.when(weatherServiceImpl.getWeather(requestParam, "yesibcastro@gmail.com")).thenReturn(DummyMock.buildWeatherRsDto());

        MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, "/meli/weather").param("email", "yesibcastro@gmail.com").param("delivery-location", "6.1543519,-75.6076758").contentType(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void getBuyerNotificationTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(weatherController).alwaysDo(print()).build();
        Mockito.when(weatherServiceImpl.getNotifications("yesibcastro@gmail.com")).thenReturn(DummyMock.buildBuyerNotificationRsDto());

        MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, "/meli/buyer-notification").param("email", "yesibcastro@gmail.com").contentType(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());

    }

    @Test
    public void getBuyerNotificationExceptionTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(weatherController).alwaysDo(print()).build();
        Mockito.when(weatherServiceImpl.getNotifications("yesibcastro@gmail.com")).thenThrow(new MeliWeatherException(ResponseCodes.MELI_BUYER_NOTIFICATION_NOT_FOUND));

        MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, "/meli/buyer-notification").param("email", "yesibcastro@gmail.com").contentType(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void getWeatherExceptionTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(weatherController).alwaysDo(print()).build();
        Map<String, Object> requestParam = WeatherMapper.requestParam(ConstantParam.KEY_VALUE, "6.1543519,-75.6076758", "2", "no", "no", "es");
        Mockito.when(weatherServiceImpl.getWeather(requestParam, "yesibcastro@gmail.com")).thenThrow(new MeliWeatherException(ResponseCodes.MELI_INTERNAL_500));

        MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, "/meli/weather").param("email", "yesibcastro@gmail.com").param("delivery-location", "6.1543519,-75.6076758").contentType(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder).andExpect(status().isUnprocessableEntity());

    }


}
