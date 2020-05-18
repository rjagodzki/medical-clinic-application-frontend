package com.medicalclinic;

import com.medicalclinic.compoments.view.AppointmentView;
import com.medicalclinic.compoments.view.DoctorView;
import com.medicalclinic.compoments.view.PatientView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;


@Route
@Viewport("width=device-width, initial-scale=1.0")
@PWA(name = "Medical Clinic Application", shortName = "Medical Clinic")
public class MainView extends VerticalLayout {
    private final MenuBar menu = new MenuBar();
    private final AppointmentView appointmentView = new AppointmentView();
    private final PatientView patientView = new PatientView();
    private final DoctorView doctorView = new DoctorView();
    private final Span title = new Span();
    private final Label welcomeText = new Label();
    private final Page page = new Page(UI.getCurrent());

    public MainView() {
        removeAll();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER, menu);
        setBoxSizing(BoxSizing.BORDER_BOX);
        getStyle().set("background", "radial-gradient(circle, rgba(176,176,180,1) 0%, rgba(153,152,163,1) 50%, rgba(117,118,119,1) 100%)");

        add(title());
        add(menu());
        add(welcomeText());
    }

    private Span title() {
        title.setText("Medical Clinic Application");
        title.getStyle().set("font-family", "\"Times New Roman\", Times, serif");
        title.getStyle().set("font-size", "40px");
        title.getStyle().set("font-weight", "700");
        title.getStyle().set("letter-spacing", "2.0px");
        title.getStyle().set("word-spacing", "3.0px");
        title.getStyle().set("cursor", "pointer");
        title.addClickListener(event -> page.reload());

        return title;
    }

    private MenuBar menu() {
        menu.setOpenOnHover(true);
        menu.getStyle().set("border", "1px solid #9E9E9E");
        menu.getStyle().set("background", "linear-gradient(193deg, rgba(255,255,255,1) 0%, rgba(173,166,166,1) 50%, rgba(255,255,255,1) 100%)");

        MenuItem appointments = menu.addItem("Appointments");
        MenuItem patients = menu.addItem("Patients");
        MenuItem doctors = menu.addItem("Doctors");

        SubMenu appointmentsSubMenu = appointments.getSubMenu();
        MenuItem newAppointment = appointmentsSubMenu.addItem("Create Appointment");
        newAppointment.addClickListener(click -> replace(getComponentAt(getComponentCount() - 1), appointmentView.createAppointment()));
        MenuItem showAppointments = appointmentsSubMenu.addItem("Show Appointments");
        showAppointments.addClickListener(click -> replace(getComponentAt(getComponentCount() - 1), appointmentView.showAppointments()));

        SubMenu patientsSubMenu = patients.getSubMenu();
        MenuItem addPatient = patientsSubMenu.addItem("Add Patient");
        addPatient.addClickListener(click -> replace(getComponentAt(getComponentCount() - 1), patientView.addPatient()));
        MenuItem showPatients = patientsSubMenu.addItem("Show List of Patients");
        showPatients.addClickListener(event -> {
            replace(getComponentAt(getComponentCount() - 1), patientView.showPatients());
        });
        SubMenu doctorsSubMenu = doctors.getSubMenu();
        MenuItem addDoctor = doctorsSubMenu.addItem("Add Doctor");
        addDoctor.addClickListener(click -> replace(getComponentAt(getComponentCount() - 1), doctorView.addDoctor()));
        MenuItem showDoctors = doctorsSubMenu.addItem("Show List of Doctors");
        showDoctors.addClickListener(click -> replace(getComponentAt(getComponentCount() - 1), doctorView.showDoctors()));

        return menu;
    }

    private Label welcomeText() {
        welcomeText.setText("Welcome to my Medical Clinic Application. " +
                "Application allows you to administer appointments, patients and doctor data. " +
                "Enjoy using it :)");

        return welcomeText;
    }
}
