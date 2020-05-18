package com.medicalclinic.service;

import com.medicalclinic.config.RequestConfig;
import com.medicalclinic.domain.Doctor;
import com.medicalclinic.domain.Patient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.management.Notification;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;


@Service
public class PatientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static PatientService patientServiceInstance = null;

    public static PatientService getInstance() {
        if (patientServiceInstance == null) {
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

    public void addPatient(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> entity = new HttpEntity<>(patient, headers);
        URI url = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_PATIENT)
                .queryParam("firstName", patient.getFirstName())
                .queryParam("lastName", patient.getLastName())
                .queryParam("dateOfBirth", patient.getDateOfBirth())
                .queryParam("placeOfBirth", patient.getPlaceOfBirth())
                .queryParam("phoneNumber", patient.getPhoneNumber())
                .queryParam("emailAddress", patient.getEmailAddress())
                .queryParam("authorizedPerson", patient.getAuthorizedPerson())
                .queryParam("active", patient.getActive()).build().encode().toUri();

        restTemplate.postForObject(url, entity, Patient.class);
    }
    public void updatePatient(Patient patient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> entity = new HttpEntity<>(patient, headers);
        URI url = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_PATIENT)
                .queryParam("patientId", patient.getPatientId())
                .queryParam("firstName", patient.getFirstName())
                .queryParam("lastName", patient.getLastName())
                .queryParam("dateOfBirth", patient.getDateOfBirth())
                .queryParam("placeOfBirth", patient.getPlaceOfBirth())
                .queryParam("phoneNumber", patient.getPhoneNumber())
                .queryParam("emailAddress", patient.getEmailAddress())
                .queryParam("authorizedPerson", patient.getAuthorizedPerson())
                .queryParam("active", patient.getActive()).build().encode().toUri();

        restTemplate.put(url, entity);
    }

}
