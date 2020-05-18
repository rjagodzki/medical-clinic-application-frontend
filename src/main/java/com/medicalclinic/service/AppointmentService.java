package com.medicalclinic.service;

import com.medicalclinic.config.RequestConfig;
import com.medicalclinic.domain.Appointment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class AppointmentService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static AppointmentService appointmentServiceInstance = null;

    public static AppointmentService getInstance() {
        if (appointmentServiceInstance == null) {
            appointmentServiceInstance = new AppointmentService();
        }
        return appointmentServiceInstance;
    }

    public List<Appointment> getAppointments() {
        URI uri = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_APPOINTMENTS)
                .build().encode().toUri();
        return Arrays.asList(ofNullable(restTemplate.getForObject(uri, Appointment[].class))
                .orElse(new Appointment[0]));
    }

    public void addAppointment(Appointment appointment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Appointment> entity = new HttpEntity<>(appointment, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(RequestConfig.SOURCE_ROOT + RequestConfig.GET_APPOINTMENT)
                .queryParam("appointmentType", appointment.getAppointmentType())
                .queryParam("appointmentDate", appointment.getAppointmentDate())
                .queryParam("appointmentTime", appointment.getAppointmentTime())
                .queryParam("createdDate", appointment.getCreatedDate())
                .queryParam("createdTime", appointment.getCreatedTime())
                .queryParam("patient", appointment.getPatient())
                .queryParam("doctor", appointment.getDoctor().getDoctorId()).build().encode().toUri();
        restTemplate.postForObject(uri, entity, Appointment.class);
    }
}
