package com.medicalclinic.service;

import com.medicalclinic.config.RequestConfig;
import com.medicalclinic.domain.Doctor;
import com.medicalclinic.domain.Patient;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.Doc;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;


@Service
public class DoctorService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static DoctorService doctorServiceInstance = null;

    public static DoctorService getInstance() {
        if (doctorServiceInstance == null) {
            doctorServiceInstance = new DoctorService();
        }
        return doctorServiceInstance;
    }

    public DoctorService() {
    }

    public List<Doctor> getDoctors() {
        URI uri = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_DOCTORS)
                .build().encode().toUri();
        return Arrays.asList(ofNullable(restTemplate.getForObject(uri, Doctor[].class))
                .orElse(new Doctor[0]));
    }

    public void addDoctor(Doctor doctor) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Doctor> entity = new HttpEntity<>(doctor, headers);
        URI url = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_DOCTOR)
                .queryParam("firstName", doctor.getFirstName())
                .queryParam("lastName", doctor.getLastName())
                .queryParam("specialization", doctor.getSpecialization())
                .queryParam("phoneNumber", doctor.getPhoneNumber())
                .queryParam("emailAddress", doctor.getEmailAddress()).build().encode().toUri();

        restTemplate.postForObject(url, entity, Doctor.class);
    }

    public void updateDoctor(Doctor doctor) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Doctor> entity = new HttpEntity<>(doctor, headers);
        URI url = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_DOCTOR)
                .queryParam("doctorId", doctor.getDoctorId())
                .queryParam("firstName", doctor.getFirstName())
                .queryParam("lastName", doctor.getLastName())
                .queryParam("specialization", doctor.getSpecialization())
                .queryParam("phoneNumber", doctor.getPhoneNumber())
                .queryParam("emailAddress", doctor.getEmailAddress()).build().encode().toUri();

        restTemplate.put(url, entity);
    }

    public void deleteDoctor(Long doctorId) {
        URI url = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_DOCTOR + "/" + doctorId)
                .build().encode().toUri();

        restTemplate.delete(url);
    }

}
