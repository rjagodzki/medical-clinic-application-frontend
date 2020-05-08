package com.medicalclinic.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Patient {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String emailAddress;
    private String phoneNumber;
    private String authorizedPerson;

    public String toString() {
        return firstName + " " + lastName;
    }
}
