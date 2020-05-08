package com.medicalclinic.compoments;

import com.medicalclinic.domain.Patient;
import com.medicalclinic.service.PatientService;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PatientView extends VerticalLayout implements HasComponents {
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField placeOfBirth = new TextField("Place Of Birth");
    private final TextField emailAddress = new TextField("E-mail Adress");
    private final TextField phoneNumber = new TextField("Phone number");
    private final TextField authorizedPerson = new TextField("Authorized Person");
    private final Grid<Patient> patientList = new Grid<>(Patient.class);
    private final PatientService patientService = PatientService.getInstance();
    private final Button save = new Button("Save");
    private Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientView() {
        Notification.show("Patient - constructor");
    }

    public VerticalLayout addPatient() {
        removeAll();
        setWidth("75%");
        Notification.show("Patient - createPatient");

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        HorizontalLayout row3 = new HorizontalLayout();
        row1.setVerticalComponentAlignment(Alignment.CENTER, row1);
        row2.setVerticalComponentAlignment(Alignment.CENTER, row2);
        row3.setVerticalComponentAlignment(Alignment.CENTER, row3);


        row1.add(firstName, lastName, dateOfBirth(),placeOfBirth);
        row2.add(emailAddress, phoneNumber, authorizedPerson);
        row3.add(save);


        add(row1);
        add(row2);
        add(row3);

        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());


        return this;
    }

    private void save() {
        Patient patient = binder.getBean();
        patientService.addPatient(patient);
    }

    public VerticalLayout showPatients() {
        removeAll();
        setWidth("100%");
        Notification.show("Patient - showPatient");
        patientList.setColumns("firstName", "lastName", "dateOfBirth", "placeOfBirth", "emailAddress","phoneNumber", "authorizedPerson");
        patientList.setItems(patientService.getPatients());
        add(patientList);


        return this;
    }

    private DatePicker dateOfBirth() {
        DatePicker dateOfBirth = new DatePicker();
        dateOfBirth.setLabel("Date of Birth");
        dateOfBirth.setReadOnly(true);
        //TODO: pobieranie daty dateOfBirth.setValue();
        return dateOfBirth;
    }
}
