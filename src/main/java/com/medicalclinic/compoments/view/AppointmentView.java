package com.medicalclinic.compoments.view;

import com.medicalclinic.domain.Appointment;
import com.medicalclinic.domain.AppointmentType;
import com.medicalclinic.domain.Doctor;
import com.medicalclinic.domain.Patient;
import com.medicalclinic.service.AppointmentService;
import com.medicalclinic.service.DoctorService;
import com.medicalclinic.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


public class AppointmentView extends VerticalLayout {
    private final ComboBox<AppointmentType> appointmentTypeComboBox = new ComboBox<>();
    private final DatePicker appointmentDate = new DatePicker();
    private final TimePicker appointmentTime = new TimePicker();
    private final DatePicker createdDate = new DatePicker();
    private final TimePicker createdTime = new TimePicker();
    private final ComboBox<Patient> patientComboBox = new ComboBox<>();
    private final ComboBox<Doctor> doctorComboBox = new ComboBox<>();

    private final Grid<Appointment> appointmentList = new Grid<>(Appointment.class);
    private final HorizontalLayout appointmentLayout = new HorizontalLayout();
    private final AppointmentService appointmentService = AppointmentService.getInstance();
    private final PatientService patientService = PatientService.getInstance();
    private final DoctorService doctorService = DoctorService.getInstance();

    private final Button saveButton = new Button("Save");

    public AppointmentView() {
    }

    public VerticalLayout createAppointment() {
        appointmentTypeComboBox.setItems(AppointmentType.values());
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
        row2.addAndExpand(appointmentTypeComboBox(), appointmentDate(), appointmentTime());
        row3.addAndExpand(patients(), doctors());
        row4.add(saveButton);

        add(row1);
        add(row2);
        add(row3);
        add(row4);

        saveButton.addClickListener(event -> save());

        return this;
    }

    public VerticalLayout showAppointments() {
        removeAll();
        setWidth("100%");
        Notification.show("Appointment - showAppointment");
        appointmentList.setColumns("appointmentType", "appointmentDate", "appointmentTime", "patient", "doctor");
        appointmentList.setItems(appointmentService.getAppointments());
        add(appointmentList);

        return this;
    }

    public void save() {
        appointmentService.addAppointment(new Appointment(
                appointmentTypeComboBox().getValue(),
                appointmentDate().getValue(),
                appointmentTime().getValue(),
                createdDate().getValue(),
                createdTime().getValue(),
                patientComboBox.getValue(),
                doctorComboBox.getValue()
        ));


    }
    private ComboBox<AppointmentType> appointmentTypeComboBox() {
        appointmentTypeComboBox.setLabel("Type");
        appointmentTypeComboBox.setPlaceholder("Choose type of appointment...");

        return appointmentTypeComboBox;
    }

    private ComboBox<Patient> patients() {

        patientComboBox.setLabel("Patient");
        patientComboBox.setPlaceholder("Choose patient...");
        patientComboBox.setClearButtonVisible(true);
        patientComboBox.setItems(patientService.getPatients());

        return patientComboBox;
    }

    private ComboBox<Doctor> doctors() {

        doctorComboBox.setLabel("Doctor");
        doctorComboBox.setPlaceholder("Chose doctor...");
        doctorComboBox.setClearButtonVisible(true);
        doctorComboBox.setItems(doctorService.getDoctors());
        return doctorComboBox;
    }

    private DatePicker createdDate() {

        createdDate.setLabel("Created Date");
        createdDate.setValue(LocalDate.now());
        createdDate.setReadOnly(true);

        return createdDate;
    }

    private DatePicker appointmentDate() {

        appointmentDate.setLabel("Appointment Date");
        appointmentDate.setPlaceholder("Choose Date...");
        appointmentDate.setClearButtonVisible(true);

        return appointmentDate;
    }

    private TimePicker createdTime() {

        createdTime.setLabel("Created Time");
        createdTime.setValue(LocalTime.now());
        createdTime.setReadOnly(true);

        return createdTime;
    }

    private TimePicker appointmentTime() {
        appointmentTime.setLabel("Appointment Time");
        appointmentTime.setPlaceholder("Choose Time");
        appointmentTime.setClearButtonVisible(true);
        appointmentTime.setStep(Duration.ofMinutes(15));


        return appointmentTime;
    }
}
