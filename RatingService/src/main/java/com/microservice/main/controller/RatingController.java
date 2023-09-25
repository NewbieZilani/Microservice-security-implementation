package com.microservice.main.controller;

import com.microservice.main.entity.RatingEntity;
import com.microservice.main.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<RatingEntity> addRating(@RequestBody RatingEntity ratingEntity){
        RatingEntity newRating = ratingService.addRating(ratingEntity);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RatingEntity>> getAllRatings(){
        List<RatingEntity> ratingEntityList= ratingService.getAllRatings();
        return new ResponseEntity<>(ratingEntityList, HttpStatus.OK);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<RatingEntity>> getRatingByUserId(@PathVariable String userId){
        List<RatingEntity> ratings = ratingService.getRatingByUserId(userId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/getByHotelId/{hotelId}")
    public ResponseEntity<List<RatingEntity>> getAllRatingByHotelId(@PathVariable String hotelId){
        List<RatingEntity> ratings= ratingService.getRatingByHotelId(hotelId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
