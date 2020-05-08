package com.medicalclinic.service;

import com.medicalclinic.config.RequestConfig;
import com.medicalclinic.domain.Patient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;


@Service
public class PatientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static PatientService patientServiceInstance = null;

    public static PatientService getInstance() {
        if(patientServiceInstance == null) {
            patientServiceInstance = new PatientService();
        }
        return patientServiceInstance;
    }

    public List<Patient> getPatients() {
        URI uri = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_PATIENTS)
                .build().encode().toUri();
        return Arrays.asList(ofNullable(restTemplate.getForObject(uri, Patient[].class))
                .orElse(new Patient[0]));
    }

    public boolean addPatient(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> responseEntity = restTemplate.exchange(RequestConfig.SOURCE_ROOT + RequestConfig.GET_PATIENT, HttpMethod.POST,
                new HttpEntity<>(patient, headers), String.class);
        return ofNullable(responseEntity.getBody()).orElse("false").equals("true");
        }
    }
