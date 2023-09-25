package com.microservice.main.controller;

import com.microservice.main.entity.HotelEntity;
import com.microservice.main.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/add")
    public ResponseEntity<HotelEntity> addHotel(@RequestBody HotelEntity hotelEntity){
        HotelEntity newHotel = hotelService.addHotel(hotelEntity);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @GetMapping("/all/hotels")
    public ResponseEntity<List<HotelEntity>> getAllHotels(){
        List<HotelEntity> hotelEntityList = hotelService.getAllHotels();
        return new ResponseEntity<>(hotelEntityList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelEntity> gethotelById(@PathVariable String id){
        HotelEntity hotel = hotelService.getHotelById(id);
        return new  ResponseEntity<>(hotel, HttpStatus.OK);
    }

}
