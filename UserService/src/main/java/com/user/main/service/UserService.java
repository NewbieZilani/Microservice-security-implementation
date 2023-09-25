package com.user.main.service;

import com.user.main.entity.Hotel;
import com.user.main.entity.Rating;
import com.user.main.entity.UserEntity;
import com.user.main.exception.CustomException;
import com.user.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RestTemplate restTemplate;

    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate=restTemplate;
    }

    public UserEntity saveUser(UserEntity userEntity){
        String randomId = UUID.randomUUID().toString();
        userEntity.setUserId(randomId);
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getAllUser(){
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error getting all users: " + e.getMessage());
        }
    }

    public UserEntity getUserById(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        Rating[] ratingArrayList= restTemplate.getForObject("http://RATING-SERVICE/ratings/getByUserId/"+userId, Rating[].class);

        List<Rating> ratings= Arrays.stream(ratingArrayList).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotelEntity= restTemplate.getForObject("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            rating.setHotel(hotelEntity);
            return rating;

        }).collect(Collectors.toList());


        user.setRatings(ratingList);
        return user;
    }
}