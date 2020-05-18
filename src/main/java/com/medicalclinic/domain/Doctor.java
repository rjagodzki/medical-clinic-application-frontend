package com.medicalclinic.domain;

import com.vaadin.flow.component.polymertemplate.Id;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Doctor {
    private Long doctorId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String specialization;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String emailAddress;

    public String toString() {
        return firstName + " " + lastName;
    }
}
