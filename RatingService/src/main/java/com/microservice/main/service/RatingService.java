package com.microservice.main.service;

import com.microservice.main.entity.RatingEntity;
import com.microservice.main.exception.CustomException;
import com.microservice.main.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public RatingEntity addRating(RatingEntity ratingEntity){
        String randomId = UUID.randomUUID().toString();
        ratingEntity.setRatingId(randomId);
        return ratingRepository.save(ratingEntity);
    }

    public List<RatingEntity> getAllRatings(){
        return ratingRepository.findAll();
    }

    public List<RatingEntity> getRatingByUserId(String userId) {
        List<RatingEntity> ratings = ratingRepository.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new CustomException("No ratings found for user with ID: " + userId);
        }
        return ratings;
    }

    public List<RatingEntity> getRatingByHotelId(String hotelId) {
        List<RatingEntity> ratings = ratingRepository.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new CustomException("No ratings found for hotel with ID: " + hotelId);
        }
        return ratings;
    }
}
