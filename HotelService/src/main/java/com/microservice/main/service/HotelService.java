package com.microservice.main.service;

import com.microservice.main.entity.HotelEntity;
import com.microservice.main.exception.CustomException;
import com.microservice.main.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelEntity addHotel(HotelEntity hotelEntity){
        String randomId = UUID.randomUUID().toString();
        hotelEntity.setId(randomId);
        return hotelRepository.save(hotelEntity);
    }

    public List<HotelEntity> getAllHotels() {
        List<HotelEntity> hotelList = hotelRepository.findAll();
        if (hotelList.isEmpty()) {
            throw new CustomException("No hotels found");
        }
        return hotelList;
    }

    public HotelEntity getHotelById(String id) {
        Optional<HotelEntity> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            return hotel.get();
        } else {
            throw new CustomException("Hotel with ID " + id + " is not found");
        }
    }

}
