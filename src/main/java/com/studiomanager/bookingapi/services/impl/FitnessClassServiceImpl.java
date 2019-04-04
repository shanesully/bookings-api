package com.studiomanager.bookingapi.services.impl;

import com.studiomanager.bookingapi.domain.FitnessClass;
import com.studiomanager.bookingapi.services.FitnessClassService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("fitnessClassService")
public class FitnessClassServiceImpl implements FitnessClassService {
    static final String uri = "http://localhost:8080/classes";

    public String getFitnessClasses() {
        //TODO Major - Remove these duplicates
        RestTemplate restTemplate = new RestTemplate();;

        return restTemplate.getForObject(uri, String.class);
    }

    public FitnessClass getFitnessClassById(int id) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uri + "/" + id, FitnessClass.class);
    }

    public FitnessClass incrementFitnessClassCapacityById(int id) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uri + "/" + id + "/increment", FitnessClass.class);
    }

    public FitnessClass decrementFitnessClassCapacityById(int id) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uri + "/" + id + "/decrement", FitnessClass.class);
    }
}
