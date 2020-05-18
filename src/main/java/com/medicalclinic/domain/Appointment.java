package com.medicalclinic.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Appointment {
    private Long appointmentId;
    @NonNull
    private AppointmentType appointmentType;
    @NonNull
    private LocalDate appointmentDate;
    @NonNull
    private LocalTime appointmentTime;
    @NonNull
    private LocalDate createdDate;
    @NonNull
    private LocalTime createdTime;
    @NonNull
    private Patient patient;
    @NonNull
    private Doctor doctor;
}
