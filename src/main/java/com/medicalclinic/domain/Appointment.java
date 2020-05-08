package com.medicalclinic.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Appointment {
    private AppointmentType appointmentType;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private String patient;
    private String doctor;
}
