package com.medicalclinic.compoments;

import com.medicalclinic.domain.Appointment;
import com.medicalclinic.domain.AppointmentType;
import com.medicalclinic.domain.Patient;
import com.medicalclinic.service.AppointmentService;
import com.medicalclinic.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AppointmentView extends VerticalLayout {
    private final Grid<Appointment> appointmentList = new Grid<>(Appointment.class);
    private final HorizontalLayout appointmentLayout = new HorizontalLayout();
    private final AppointmentService appointmentService = AppointmentService.getInstance();
    private final PatientService patientService = PatientService.getInstance();
    private Binder <Appointment> binder = new Binder<>(Appointment.class);

    public AppointmentView() {
        Notification.show("Appointment - konstruktor");
        getStyle().set("background", "radial-gradient(circle, rgba(255,255,255,1) 0%, rgba(178,224,232,1) 50%, rgba(255,255,255,1) 100%)");
        getStyle().set("border", "10px solid #534b4b");
    }

    public VerticalLayout createAppointment() {
        removeAll();
        setWidth("50%");
        Notification.show("Appointment - createAppointment");
        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        HorizontalLayout row3 = new HorizontalLayout();
        HorizontalLayout row4 = new HorizontalLayout();
        row1.setVerticalComponentAlignment(Alignment.CENTER, row1);

        row4.setVerticalComponentAlignment(Alignment.CENTER, row4);

        row1.add(createdDate(), createdTime());
        row2.addAndExpand(new ComboBox<AppointmentType>("Type"), appointmentDate(), appointmentTime());
        row3.addAndExpand(patients(), doctors());
        row4.add(new Button("Save"));
        add(row1);
        add(row2);
        add(row3);
        add(row4);

        return this;
    }

    public VerticalLayout showAppointments() {
        removeAll();
        setWidth("100%");
        Notification.show("Appointment - showAppointment");
        appointmentList.setColumns("appointmentType", "appointmentDate", "appointmentTime", "patient", "doctor", "createdDate", "createdTime");
        appointmentList.setItems(appointmentService.getAppointments());
        add(appointmentList);


        return this;
    }

    public void save() {

    }

    private ComboBox<Patient> patients() {
        ComboBox<Patient> patients = new ComboBox<>();
        patients.setLabel("Patient");
        patients.setPlaceholder("Choose patient");
        patients.setClearButtonVisible(true);


        patients.setItems(patientService.getPatients());


        return patients;
    }

    private ComboBox doctors() {
        ComboBox doctors = new ComboBox();
        doctors.setLabel("Doctor");
        doctors.setPlaceholder("Chose doctor");
        doctors.setClearButtonVisible(true);

        return doctors;
    }


    private DatePicker createdDate() {
        DatePicker createdDate = new DatePicker();
        createdDate.setLabel("Created Date");
        createdDate.setValue(LocalDate.now());
        createdDate.setReadOnly(true);

        return createdDate;
    }

    private DatePicker appointmentDate() {
        DatePicker appointmentDate = new DatePicker();
        appointmentDate.setLabel("Appointment Date");
        appointmentDate.setPlaceholder("Choose Date");
        appointmentDate.setClearButtonVisible(true);

        return appointmentDate;
    }

    private TimePicker createdTime() {
        TimePicker createdTime = new TimePicker();
        createdTime.setLabel("Created Time");
        createdTime.setValue(LocalTime.now());
        createdTime.setReadOnly(true);

        return createdTime;
    }

    private TimePicker appointmentTime() {
        TimePicker appointmentTime = new TimePicker();
        appointmentTime.setLabel("Appointment Time");
        appointmentTime.setPlaceholder("Choose Time");
        appointmentTime.setClearButtonVisible(true);
        appointmentTime.setStep(Duration.ofMinutes(15));


        return appointmentTime;
    }
}
