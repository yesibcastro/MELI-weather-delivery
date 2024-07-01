package com.meli.weather.dataprovider.jpa.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BUYER_NOTIFICATION")
public class BuyerNotificationEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NOTIFICATION")
    private LocalDateTime notification;

    @Column(name = "DELIVERY_LOCATION")
    private String deliveryLocation;

    @Column(name = "FORECAST_CODE")
    private int forecastCode;

    @Column(name = "FORECAST")
    private String forecast;
}
