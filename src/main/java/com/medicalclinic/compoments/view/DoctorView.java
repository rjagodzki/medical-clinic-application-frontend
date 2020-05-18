package com.medicalclinic.compoments.view;

import com.medicalclinic.domain.Doctor;
import com.medicalclinic.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DoctorView extends VerticalLayout {
    private final DoctorService doctorService = DoctorService.getInstance();
    private final TextField firstNameTextField = new TextField("First Name");
    private final TextField lastNameTextField = new TextField("Last Name");
    private final TextField specializationTextField = new TextField("Specialization");
    private final TextField phoneNumberTextField = new TextField("Phone Number");
    private final TextField emailAddressTextField = new TextField("Email Address");
    private final Button saveButton = new Button("Save");

    private final Grid<Doctor> doctorGrid = new Grid<>();
    private final ListDataProvider<Doctor> dataProvider = new ListDataProvider<>(doctorService.getDoctors());

    private final Editor<Doctor> editor = doctorGrid.getEditor();
    private final Binder<Doctor> binder = new Binder<>(Doctor.class);

    private final Button saveDivButton = new Button("Save", e -> editor.save());
    private final Button cancelDivButton = new Button("Cancel", e -> editor.cancel());
    private final Div editButtonsDiv = new Div(saveDivButton, cancelDivButton);

    public DoctorView() {
    }

    public VerticalLayout addDoctor() {
        removeAll();
        setWidth("50%");

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        HorizontalLayout row3 = new HorizontalLayout();
        row1.setVerticalComponentAlignment(Alignment.CENTER, row1);
        row2.setVerticalComponentAlignment(Alignment.CENTER, row2);
        row3.setVerticalComponentAlignment(Alignment.CENTER, row3);

        row1.add(firstNameTextField, lastNameTextField, specializationTextField);
        row2.add(phoneNumberTextField, emailAddressTextField);
        row3.add(saveButton);

        add(row1);
        add(row2);
        add(row3);

        saveButton.addClickListener(event -> {
            if (areFieldsFiled()) {
                save();
                Notification.show("New Doctor has been added: " + firstNameTextField.getValue() + " " + lastNameTextField.getValue(), 5000, Notification.Position.TOP_CENTER);
            } else {
                Notification.show("File all fields before saving data!", 5000, Notification.Position.TOP_CENTER);
            }
        });

        return this;
    }


    public VerticalLayout showDoctors() {
        removeAll();
        setWidth("100%");

        editor.setBinder(binder);
        editor.setBuffered(false);
        doctorGrid.setDataProvider(dataProvider);

        Grid.Column<Doctor> firstNameColumn = doctorGrid.addColumn(Doctor::getFirstName).setHeader("First Name");
        Grid.Column<Doctor> lastNameColumn = doctorGrid.addColumn(Doctor::getLastName).setHeader("Last Name");
        Grid.Column<Doctor> specializationColumn = doctorGrid.addColumn(Doctor::getSpecialization).setHeader("Specialization");
        Grid.Column<Doctor> phoneNumberColumn = doctorGrid.addColumn(Doctor::getPhoneNumber).setHeader("Phone Number");
        Grid.Column<Doctor> emailAddressColumn = doctorGrid.addColumn(Doctor::getEmailAddress).setHeader("E-mail Adress");

        binder.forField(firstNameTextField).bind(Doctor::getFirstName, Doctor::setFirstName);
        binder.forField(lastNameTextField).bind(Doctor::getLastName, Doctor::setLastName);
        binder.forField(specializationTextField).bind(Doctor::getSpecialization, Doctor::setSpecialization);
        binder.forField(phoneNumberTextField).bind(Doctor::getPhoneNumber, Doctor::setPhoneNumber);
        binder.forField(emailAddressTextField).bind(Doctor::getEmailAddress, Doctor::setEmailAddress);

        firstNameColumn.setEditorComponent(firstNameTextField);
        lastNameColumn.setEditorComponent(lastNameTextField);
        specializationColumn.setEditorComponent(specializationTextField);
        phoneNumberColumn.setEditorComponent(phoneNumberTextField);
        emailAddressColumn.setEditorComponent(emailAddressTextField);

        HeaderRow filterRow = doctorGrid.appendHeaderRow();

        TextField firstNameFilter = new TextField();
        firstNameFilter.addValueChangeListener(event -> dataProvider.addFilter(doctor ->
                StringUtils.containsIgnoreCase(doctor.getFirstName(), firstNameFilter.getValue())));
        firstNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(firstNameColumn).setComponent(firstNameFilter);
        firstNameFilter.setSizeFull();
        firstNameFilter.setPlaceholder("Filter by First Name...");

        TextField lastNameFilter = new TextField();
        lastNameFilter.addValueChangeListener(event -> dataProvider.addFilter(doctor ->
                StringUtils.containsIgnoreCase(doctor.getLastName(), lastNameFilter.getValue())));
        lastNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(lastNameColumn).setComponent(lastNameFilter);
        lastNameFilter.setSizeFull();
        lastNameFilter.setPlaceholder("Filter by Last Name...");

        TextField specializationFilter = new TextField();
        specializationFilter.addValueChangeListener(event -> dataProvider.addFilter(doctor ->
                StringUtils.containsIgnoreCase(doctor.getSpecialization(), specializationFilter.getValue())));
        specializationFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(specializationColumn).setComponent(specializationFilter);
        specializationFilter.setSizeFull();
        specializationFilter.setPlaceholder("Filter by Specialization...");

        TextField phoneNumberFilter = new TextField();
        phoneNumberFilter.addValueChangeListener(event -> dataProvider.addFilter(doctor ->
                StringUtils.containsIgnoreCase(doctor.getPhoneNumber(), phoneNumberFilter.getValue())));
        phoneNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(phoneNumberColumn).setComponent(phoneNumberFilter);
        phoneNumberFilter.setSizeFull();
        phoneNumberFilter.setPlaceholder("Filter by Phone Number...");

        TextField emailFilter = new TextField();
        emailFilter.addValueChangeListener(event -> dataProvider.addFilter(doctor ->
                StringUtils.containsIgnoreCase(doctor.getEmailAddress(), emailFilter.getValue())));
        emailFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(emailAddressColumn).setComponent(emailFilter);
        emailFilter.setSizeFull();
        emailFilter.setPlaceholder("Filter by E-mail Address...");

        Grid.Column<Doctor> editorColumn = doctorGrid.addComponentColumn(doctor -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(event -> editor.editItem(doctor));
            editButton.setEnabled(!editor.isOpen());
            return editButton;
        });
        saveDivButton.addClickListener(event -> {
            doctorService.updateDoctor(binder.getBean());
            editor.closeEditor();
            Notification.show("Doctor's data has been changed", 5000, Notification.Position.TOP_CENTER);
            dataProvider.refreshAll();
        });
        editorColumn.setEditorComponent(editButtonsDiv);

        Grid.Column<Doctor> deleteColumn = doctorGrid.addComponentColumn(doctor -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(event -> doctorService.deleteDoctor(doctor.getDoctorId()));
            return deleteButton;
        });

        add(doctorGrid);

        return this;
    }

    private void save() {
        doctorService.addDoctor(new Doctor(
                firstNameTextField.getValue(),
                lastNameTextField.getValue(),
                specializationTextField.getValue(),
                phoneNumberTextField.getValue(),
                emailAddressTextField.getValue()));
        dataProvider.refreshAll();
    }

    private boolean areFieldsFiled() {
        for (TextField textField : fields()) {
            if (textField.getValue().equals(""))
                return false;
        }
        return true;
    }

    private List<TextField> fields() {
        List<TextField> fields = new ArrayList<>();
        fields.add(firstNameTextField);
        fields.add(lastNameTextField);
        fields.add(specializationTextField);
        fields.add(phoneNumberTextField);
        fields.add(emailAddressTextField);

        return fields;
    }

}
