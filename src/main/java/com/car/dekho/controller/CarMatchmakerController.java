package com.car.dekho.controller;

import com.car.dekho.dto.CarMatchRequest;
import com.car.dekho.dto.CarMatchResponse;
import com.car.dekho.service.CarMatchmakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car-matchmaker")
public class CarMatchmakerController {

    @Autowired
    private CarMatchmakerService carMatchmakerService;

    @PostMapping("/match")
    public CarMatchResponse matchCars(@RequestBody CarMatchRequest request) {
        return carMatchmakerService.matchCars(request);
    }

    @GetMapping("/health")
    public String health() {
        return "Car Matchmaker API is running";
    }
}


