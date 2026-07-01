package com.car.dekho.config;

import com.car.dekho.repo.CarRepository;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.car.dekho.model.Car;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.util.Arrays;

@Component
public class DataLoader {

    @Autowired
    private CarRepository repo;

    @PostConstruct
    public void loadData() {
        try {
            if (repo.count() > 0) return; // don't re-load if already present
            ClassPathResource resource = new ClassPathResource("cars.json");
            try (InputStream is = resource.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                Car[] cars = mapper.readValue(is, Car[].class);
                repo.saveAll(Arrays.asList(cars));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
