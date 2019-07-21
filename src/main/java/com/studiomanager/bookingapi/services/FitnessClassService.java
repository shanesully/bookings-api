package com.studiomanager.bookingapi.services;

import com.studiomanager.bookingapi.domain.FitnessClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FitnessClassService {

    private static final String HOST = "http://localhost";
    private static final String CLASSES_ENDPOINT = "/classes";
    private static final String URL = HOST + ":" + CLASSES_ENDPOINT;

    private static final Logger LOGGER = LoggerFactory.getLogger(SLF4JExample.class);

    public String getFitnessClasses() {
        RestTemplate restTemplate = new RestTemplate();;

        return restTemplate.getForObject(URL, String.class);
    }

    public FitnessClass getFitnessClassById(int id) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(URL + "/" + id, FitnessClass.class);
    }
}
