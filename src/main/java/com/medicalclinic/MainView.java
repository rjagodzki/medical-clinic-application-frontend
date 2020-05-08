package com.medicalclinic;

import com.medicalclinic.compoments.AppointmentView;
import com.medicalclinic.compoments.DoctorView;
import com.medicalclinic.compoments.PatientView;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.awt.*;


@Route
@Viewport("width=device-witdh, inital-scale=1.0")
@PWA(name = "Medical Clinic Application", shortName = "Medical Clinic")
public class MainView extends VerticalLayout {
    private MenuBar menu = new MenuBar();
    private AppointmentView appointmentView = new AppointmentView();
    private PatientView patientView = new PatientView();
    private DoctorView doctorView = new DoctorView();



    public MainView() {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, menu);
        getStyle().set("background", "radial-gradient(circle, rgba(176,176,180,1) 0%, rgba(153,152,163,1) 50%, rgba(117,118,119,1) 100%)");
        menu.getStyle().set("border", "1px solid #9E9E9E");


//        setJustifyContentMode(JustifyContentMode.BETWEEN);

        setBoxSizing(BoxSizing.BORDER_BOX);

        // FIXME: Ladny tytul aplikacji
        Span label = new Span("Medical Clinic Application");

        label.getStyle().set("font-family", "\"Times New Roman\", Times, serif");
        label.getStyle().set("font-size", "40px");
        label.getStyle().set("font-weight", "700");
        label.getStyle().set("letter-spacing", "2.0px");
        label.getStyle().set("word-spacing", "3.0px");

        add(label);
        add(menu());
//        add(menu);

//         MENU

//        menu.setVisible(true);
    }


    private MenuBar menu() {
//        menu.addThemeVariants(MenuBarVariant.LUMO_ICON);
        menu.setOpenOnHover(true);

        menu.getStyle().set("background", "linear-gradient(193deg, rgba(255,255,255,1) 0%, rgba(173,166,166,1) 50%, rgba(255,255,255,1) 100%)");
//        menu.getStyle().set("font-color", "#000000");
//        menu.getStyle().set("font-size", "25px");

        MenuItem appointments = menu.addItem("Appointments");
        MenuItem patients = menu.addItem("Patients");
        MenuItem doctors = menu.addItem("Doctors");

        SubMenu appointmentsSubMenu = appointments.getSubMenu();
        MenuItem newAppointment = appointmentsSubMenu.addItem("Create Appointment");
        newAppointment.addClickListener(click -> add(appointmentView.createAppointment()));
        MenuItem showAppointments = appointmentsSubMenu.addItem("Show Appointments");
        showAppointments.addClickListener(event -> appointmentView.showAppointments());

        SubMenu patientsSubMenu = patients.getSubMenu();
        MenuItem addPatient = patientsSubMenu.addItem("Add Patient");
        addPatient.addClickListener(click -> add(patientView.addPatient()));
        MenuItem showPatients = patientsSubMenu.addItem("Show List of Patients");
        showPatients.addClickListener(click -> add(patientView.showPatients()));

        SubMenu doctorsSubMenu = doctors.getSubMenu();
        MenuItem addDoctor = doctorsSubMenu.addItem("Add Doctor");
        addDoctor.addClickListener(click ->  add(doctorView.addDoctor()));
        MenuItem showDoctors = doctorsSubMenu.addItem("Show List of Doctors");
        showDoctors.addClickListener(click -> add(doctorView.showDoctors()));

        return menu;
    }
}
