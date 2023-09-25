package com.microservice.main.repository;

import com.microservice.main.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity, String> {
    List<RatingEntity> findByUserId(String userId);
    List<RatingEntity> findByHotelId(String hotelId);
}
