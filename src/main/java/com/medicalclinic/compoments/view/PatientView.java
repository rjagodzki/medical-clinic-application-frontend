package com.medicalclinic.compoments.view;

import com.medicalclinic.domain.Patient;
import com.medicalclinic.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PatientView extends VerticalLayout {
    private final TextField firstNameTextField = new TextField("First Name");
    private final TextField lastNameTextField = new TextField("Last Name");
    private final DatePicker dateOfBirthField = new DatePicker();
    private final TextField placeOfBirthTextField = new TextField("Place Of Birth");
    private final TextField emailAddressTextField = new TextField("E-mail Adress");
    private final TextField phoneNumberTextField = new TextField("Phone number");
    private final TextField authorizedPersonTextField = new TextField("Authorized Person");
    private final Select<Boolean> activeField = new Select<>(true, false);

    private final Button saveButton = new Button("Save");

    private final PatientService patientService = PatientService.getInstance();
    private final ListDataProvider<Patient> dataProvider = new ListDataProvider<>(patientService.getPatients());
    private final Grid<Patient> patientGrid = new Grid<>();
    private final Editor<Patient> editor = patientGrid.getEditor();
    private final Binder<Patient> binder = new Binder<>(Patient.class);

    private final Button saveDivButton = new Button("Save", e -> editor.save());
    private final Button cancelDivButton = new Button("Cancel", e -> editor.cancel());
    private final Div editButtonsDiv = new Div(saveDivButton, cancelDivButton);

    public PatientView() {
    }
    public VerticalLayout addPatient() {
        removeAll();
        setWidth("75%");

        HorizontalLayout row1 = new HorizontalLayout();
        HorizontalLayout row2 = new HorizontalLayout();
        HorizontalLayout row3 = new HorizontalLayout();
        row1.setVerticalComponentAlignment(Alignment.CENTER, row1);
        row2.setVerticalComponentAlignment(Alignment.CENTER, row2);
        row3.setVerticalComponentAlignment(Alignment.CENTER, row3);

        activeField.setLabel("Active");
        activeField.setValue(true);
        dateOfBirth().setValue(LocalDate.now());

        row1.add(firstNameTextField, lastNameTextField, dateOfBirth(), placeOfBirthTextField);
        row2.add(phoneNumberTextField, emailAddressTextField, authorizedPersonTextField, activeField);
        row3.add(saveButton);

        add(row1);
        add(row2);
        add(row3);

        saveButton.addClickListener(event -> {
            if (areFieldsFiled() ) {
                save();
                Notification.show("New Patient has been added: " + firstNameTextField.getValue() + " " + lastNameTextField.getValue(),5000, Notification.Position.TOP_CENTER);
            } else {
                Notification.show("File all fields before saving data!", 5000, Notification.Position.TOP_CENTER);
            }
        });
        return this;
    }

    public VerticalLayout showPatients() {
        removeAll();
        setWidthFull();

        editor.setBinder(binder);
        editor.setBuffered(false);
        patientGrid.setDataProvider(dataProvider);

        Grid.Column<Patient> firstNameColumn = patientGrid.addColumn(Patient::getFirstName).setHeader("First Name");
        Grid.Column<Patient> lastNameColumn = patientGrid.addColumn(Patient::getLastName).setHeader("Last Name");
        Grid.Column<Patient> dateOfBirthColumn = patientGrid.addColumn(Patient::getDateOfBirth).setHeader("Date of Birth");
        Grid.Column<Patient> placeOfBirthColumn = patientGrid.addColumn(Patient::getPlaceOfBirth).setHeader("Place of Birth");
        Grid.Column<Patient> phoneNumberColumn = patientGrid.addColumn(Patient::getPhoneNumber).setHeader("Phone Number");
        Grid.Column<Patient> emailAddressColumn = patientGrid.addColumn(Patient::getEmailAddress).setHeader("E-mail Address");
        Grid.Column<Patient> authorizedPersonColumn = patientGrid.addColumn(Patient::getAuthorizedPerson).setHeader("Authorized Person");
        Grid.Column<Patient> activeColumn = patientGrid.addColumn(Patient::getActive).setHeader("Active");

        binder.forField(firstNameTextField).bind(Patient::getFirstName, Patient::setFirstName);
        binder.forField(lastNameTextField).bind(Patient::getLastName, Patient::setLastName);
        binder.forField(dateOfBirthField).bind(Patient::getDateOfBirth, Patient::setDateOfBirth);
        binder.forField(placeOfBirthTextField).bind(Patient::getPlaceOfBirth, Patient::setPlaceOfBirth);
        binder.forField(phoneNumberTextField).bind(Patient::getPhoneNumber, Patient::setPhoneNumber);
        binder.forField(emailAddressTextField).bind(Patient::getEmailAddress, Patient::setEmailAddress);
        binder.forField(authorizedPersonTextField).bind(Patient::getAuthorizedPerson, Patient::setAuthorizedPerson);
        binder.forField(activeField).bind(Patient::getActive, Patient::setActive);

        firstNameColumn.setEditorComponent(firstNameTextField);
        lastNameColumn.setEditorComponent(lastNameTextField);
        dateOfBirthColumn.setEditorComponent(dateOfBirthField);
        placeOfBirthColumn.setEditorComponent(placeOfBirthTextField);
        phoneNumberColumn.setEditorComponent(phoneNumberTextField);
        emailAddressColumn.setEditorComponent(emailAddressTextField);
        authorizedPersonColumn.setEditorComponent(authorizedPersonTextField);
        activeColumn.setEditorComponent(activeField);

        HeaderRow filterRow = patientGrid.appendHeaderRow();

        TextField firstNameFilter = new TextField();
        firstNameFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsIgnoreCase(patient.getFirstName(), firstNameFilter.getValue())));
        firstNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(firstNameColumn).setComponent(firstNameFilter);
        firstNameFilter.setSizeFull();
        firstNameFilter.setPlaceholder("Filter by First Name...");

        TextField lastNameFilter = new TextField();
        lastNameFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsIgnoreCase(patient.getLastName(), lastNameFilter.getValue())));
        lastNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(lastNameColumn).setComponent(lastNameFilter);
        lastNameFilter.setSizeFull();
        lastNameFilter.setPlaceholder("Filter by Last Name...");

        DatePicker dateOfBirthFilter = new DatePicker();
        dateOfBirthFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsOnly(patient.getDateOfBirth().toString(), dateOfBirthFilter.getValue().toString())));
        filterRow.getCell(dateOfBirthColumn).setComponent(dateOfBirthFilter);
        dateOfBirthFilter.setSizeFull();
        dateOfBirthFilter.setPlaceholder("Filter by Date of Birth...");

        TextField placeOfBirthFilter = new TextField();
        placeOfBirthFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsIgnoreCase(patient.getPlaceOfBirth(), placeOfBirthFilter.getValue())));
        placeOfBirthFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(placeOfBirthColumn).setComponent(placeOfBirthFilter);
        placeOfBirthFilter.setSizeFull();
        placeOfBirthFilter.setPlaceholder("Filter by Place of Birth...");

        TextField phoneNumberFilter = new TextField();
        phoneNumberFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsIgnoreCase(patient.getPhoneNumber(), phoneNumberFilter.getValue())));
        phoneNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(phoneNumberColumn).setComponent(phoneNumberFilter);
        phoneNumberFilter.setSizeFull();
        phoneNumberFilter.setPlaceholder("Filter by Phone Number...");

        TextField emailAddressFilter = new TextField();
        emailAddressFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsIgnoreCase(patient.getEmailAddress(), emailAddressFilter.getValue())));
        emailAddressFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(emailAddressColumn).setComponent(emailAddressFilter);
        emailAddressFilter.setSizeFull();
        emailAddressFilter.setPlaceholder("Filter by E-mail Address...");

        TextField authorizedPersonFilter = new TextField();
        authorizedPersonFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                StringUtils.containsIgnoreCase(patient.getAuthorizedPerson(), emailAddressFilter.getValue())));
        authorizedPersonFilter.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(authorizedPersonColumn).setComponent(authorizedPersonFilter);
        authorizedPersonFilter.setSizeFull();
        authorizedPersonFilter.setPlaceholder("Filter by Authorized Person...");

        Select<Boolean> activeFilter = new Select<>(true, false);
        activeFilter.addValueChangeListener(event -> dataProvider.addFilter(patient ->
                BooleanUtils.toBooleanDefaultIfNull(patient.getActive(), activeField.getValue())));
        filterRow.getCell(activeColumn).setComponent(activeFilter);
        activeFilter.setSizeFull();
        activeFilter.setPlaceholder("Filter by Active..");


        Grid.Column<Patient> editorColumn = patientGrid.addComponentColumn(patient -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(event -> {
                editor.editItem(patient);
            });
            editButton.setEnabled(!editor.isOpen());
            return editButton;
        });
        saveDivButton.addClickListener(event -> {
            patientService.updatePatient(binder.getBean());
            editor.closeEditor();
            Notification.show("Patient's data has been changed", 5000, Notification.Position.TOP_CENTER);
            dataProvider.refreshAll();
        });
        editorColumn.setEditorComponent(editButtonsDiv);

        add(patientGrid);

        return this;
    }

    private void save() {
        patientService.addPatient(new Patient(
                firstNameTextField.getValue(),
                lastNameTextField.getValue(),
                dateOfBirthField.getValue(),
                placeOfBirthTextField.getValue(),
                phoneNumberTextField.getValue(),
                emailAddressTextField.getValue(),
                authorizedPersonTextField.getValue(),
                activeField.getValue()));
    }

    private boolean areFieldsFiled() {
        for (TextField textField : fields()) {
            if (textField.getValue().equals(""))
                return false;
        }
        return true;
    }

    private DatePicker dateOfBirth() {
        dateOfBirthField.setLabel("Date of Birth");
        dateOfBirthField.setPlaceholder("Choose Date...");

        return dateOfBirthField;
    }

    private List<TextField> fields() {
        List<TextField> fields = new ArrayList<>();
        fields.add(firstNameTextField);
        fields.add(lastNameTextField);
        fields.add(placeOfBirthTextField);
        fields.add(phoneNumberTextField);
        fields.add(authorizedPersonTextField);

        return fields;
    }
}
