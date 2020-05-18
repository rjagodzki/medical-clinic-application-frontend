package com.medicalclinic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Patient {
    private Long patientId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NonNull
    private String placeOfBirth;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String emailAddress;
    @NonNull
    private String authorizedPerson;
    @NonNull
    private Boolean active;

    public String toString() {
        return firstName + " " + lastName;
    }
}
