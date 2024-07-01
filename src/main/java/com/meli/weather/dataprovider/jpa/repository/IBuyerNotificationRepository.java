package com.meli.weather.dataprovider.jpa.repository;

import com.meli.weather.dataprovider.jpa.entity.BuyerNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBuyerNotificationRepository extends JpaRepository<BuyerNotificationEntity,Long> {

    Optional<List<BuyerNotificationEntity>> findByEmail(String email);
}
