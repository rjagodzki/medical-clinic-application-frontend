package com.medicalclinic.domain;

import com.vaadin.flow.component.polymertemplate.Id;
import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Doctor {
//    private String doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String phoneNumber;
    private String emailAddress;
}
