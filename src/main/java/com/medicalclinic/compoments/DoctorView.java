package com.medicalclinic.compoments;

import com.medicalclinic.domain.Doctor;
import com.medicalclinic.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class DoctorView extends VerticalLayout {
    private DoctorService doctorService = DoctorService.getInstance();
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField specialization = new TextField("Specialization");
    private TextField phoneNumber = new TextField("Specialization");
    private TextField emailAddress = new TextField("Specialization");
    private Grid<Doctor> doctorList = new Grid<>(Doctor.class);
    private Button save = new Button("Save");

    public DoctorView() {
        Notification.show("Doctor - constructor");
    }

    public VerticalLayout addDoctor() {
        removeAll();
        setWidth("50%");
        Notification.show("Doctor - addDoctor");
        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        HorizontalLayout row3 = new HorizontalLayout();
        row1.setVerticalComponentAlignment(Alignment.CENTER, row1);
        row2.setVerticalComponentAlignment(Alignment.CENTER, row2);
        row3.setVerticalComponentAlignment(Alignment.CENTER, row3);
        row1.add(firstName, lastName, specialization);
        row2.add(phoneNumber, emailAddress);
        row3.add(save);

        add(row1);
        add(row2);
        add(row3);


        save.addClickListener(event -> save());

        return this;
    }

    private void save() {
        doctorService.addDoctor(new Doctor(
                firstName.getValue(),
                lastName.getValue(),
                specialization.getValue(),
                phoneNumber.getValue(),
                emailAddress.getValue()));
    }

    public VerticalLayout showDoctors() {
        removeAll();
        setWidth("75%");
        Notification.show("Doctor - showDoctors");
        doctorList.setColumns("firstName", "lastName", "specialization");
        add(doctorList);

        return this;
    }
}
