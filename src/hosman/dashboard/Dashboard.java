package hosman.dashboard;

// admissions
import hosman.Admission;
import hosman.dashboard.admissions.AddAdmission;
import hosman.dashboard.admissions.ChangeAdmission;

// patients
import hosman.Patient;
import hosman.dashboard.patient.AddPatient;
import hosman.dashboard.patient.ChangePatient;

// staff
import hosman.Staff;
import hosman.dashboard.staff.AddStaff;
import hosman.dashboard.staff.ChangeStaff;

// rooms
import hosman.Room;
import hosman.dashboard.rooms.AddRoom;
import hosman.dashboard.rooms.ChangeRoom;

// hospital
import hosman.Hospital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.binding.Bindings;
import java.util.ArrayList;

public class Dashboard implements Initializable {
    private static final String title = new String("HosMan Dashboard");
    private Hospital hospital;
    private Stage stage;

    public void alert(String message) {
        // display message box
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public boolean prompt(String message) {
        // display prompt
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);

        if (alert.showAndWait().get() == ButtonType.OK)
            return true;
        else
            return false;
    }

    @FXML private ProgressIndicator prgPatients, prgStaff;
    @FXML private Label txtPatientsStatus, txtStaffStatus;

    @FXML private TableView tblMedicine;

    // rooms
    @FXML private TableView<RoomData> tblRooms;
    @FXML private TableColumn<RoomData, String> roomID;
    @FXML private TableColumn<RoomData, String> roomDescription;
    @FXML private TableColumn<RoomData, String> roomCapacity;
    @FXML private TableColumn<RoomData, String> roomStatus;
    private final ObservableList<RoomData> roomData = FXCollections.observableArrayList();

    // staff
    @FXML private TableView<StaffData> tblStaff;
    @FXML private TableColumn<StaffData, String> staffID;
    @FXML private TableColumn<StaffData, String> staffTitle;
    @FXML private TableColumn<StaffData, String> staffFirstName;
    @FXML private TableColumn<StaffData, String> staffSurname;
    @FXML private TableColumn<StaffData, String> staffPosition;
    @FXML private TableColumn<StaffData, String> staffStatus;
    @FXML private TableColumn<StaffData, String> staffContact;
    @FXML private TableColumn<StaffData, String> staffAddress;
    private final ObservableList<StaffData> staffData = FXCollections.observableArrayList();

    // patient
    @FXML private TableView<PatientData> tblPatients;
    @FXML private TableColumn<PatientData, String> patientID;
    @FXML private TableColumn<PatientData, String> patientFirstName;
    @FXML private TableColumn<PatientData, String> patientSurname;
    @FXML private TableColumn<PatientData, String> patientStatus;
    @FXML private TableColumn<PatientData, String> patientCurrentDiagnosis;
    @FXML private TableColumn<PatientData, String> patientContact;
    @FXML private TableColumn<PatientData, String> patientAddress;
    private final ObservableList<PatientData> patientData = FXCollections.observableArrayList();

    // admission
    @FXML private TableView<AdmissionData> tblAdmissions;
    @FXML private TableColumn<AdmissionData, String> admissionID;
    @FXML private TableColumn<AdmissionData, String> admissionPatientInfo;
    @FXML private TableColumn<AdmissionData, String> admissionAdmissionDate;
    @FXML private TableColumn<AdmissionData, String> admissionDischargeDate;
    @FXML private TableColumn<AdmissionData, String> admissionRoomInfo;
    @FXML private TableColumn<AdmissionData, String> admissionStaffInfo;
    private final ObservableList<AdmissionData> admissionData = FXCollections.observableArrayList();

    // current user
    @FXML private Label lblCurrentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // room
        roomID.setCellValueFactory(new PropertyValueFactory<RoomData, String>("roomID"));
        roomDescription.setCellValueFactory(new PropertyValueFactory<RoomData, String>("roomDescription"));
        roomCapacity.setCellValueFactory(new PropertyValueFactory<RoomData, String>("roomCapacity"));
        roomStatus.setCellValueFactory(new PropertyValueFactory<RoomData, String>("roomStatus"));

        tblRooms.setRowFactory(new Callback<TableView<RoomData>, TableRow<RoomData>>() {
            @Override
            public TableRow<RoomData> call(TableView<RoomData> tableView) {
                final TableRow<RoomData> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();

                // make Change menu item
                final MenuItem changeMenuItem = new MenuItem("Change");
                changeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // get room data
                        RoomData data = row.getItem();
                        Room room = convertRoomData(data);

                        try {
                            ChangeRoom cr = new ChangeRoom(hospital, stage, room);
                            if (cr.refresh()) {
                                System.out.println("Refresh");
                                updateProgressIndicators();
                                updateRoomTable();
                            }
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // make Remove menu item
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!prompt("Are you sure you would like to remove the selected room?"))
                            return;

                        // get room data
                        RoomData data = row.getItem();
                        Room room = convertRoomData(data);

                        try {
                            // remove room from database
                            hospital.removeRoom(room);

                            // remove menu item
                            tblRooms.getItems().remove(row.getItem());
                            updateProgressIndicators();
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // add menu items to context menu
                contextMenu.getItems().add(changeMenuItem);
                contextMenu.getItems().add(removeMenuItem);

                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );

                return row ;
            }
        });

        // staff
        staffID.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffID"));
        staffTitle.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffTitle"));
        staffFirstName.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffFirstName"));
        staffSurname.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffSurname"));
        staffPosition.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffPosition"));
        staffStatus.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffStatus"));
        staffContact.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffContact"));
        staffAddress.setCellValueFactory(new PropertyValueFactory<StaffData, String>("staffAddress"));

        tblStaff.setRowFactory(new Callback<TableView<StaffData>, TableRow<StaffData>>() {
            @Override
            public TableRow<StaffData> call(TableView<StaffData> tableView) {
                final TableRow<StaffData> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();

                // make Change menu item
                final MenuItem changeMenuItem = new MenuItem("Change");
                changeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // get staff data
                        StaffData data = row.getItem();
                        Staff staff = convertStaffData(data);

                        try {
                            ChangeStaff cs = new ChangeStaff(hospital, stage, staff);
                            if (cs.refresh()) {
                                System.out.println("Refresh");
                                data = convertStaffData(cs.getStaff());

                                staffData.clear();
                                for (Staff sf : hospital.getStaff())
                                    staffData.add(convertStaffData(sf));

                                tblStaff.setItems(staffData);
                                updateProgressIndicators();
                            }
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // make Remove menu item
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!prompt("Are you sure you would like to remove the selected staff member?"))
                            return;

                        // get staff data
                        StaffData data = row.getItem();
                        Staff staff = convertStaffData(data);

                        try {
                            // remove staff from database
                            hospital.removeStaff(staff);

                            // remove menu item
                            tblStaff.getItems().remove(row.getItem());
                            updateProgressIndicators();
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // add menu items to context menu
                contextMenu.getItems().add(changeMenuItem);
                contextMenu.getItems().add(removeMenuItem);

                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );

                return row ;
            }
        });

        // patient
        patientID.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientID"));
        patientFirstName.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientFirstName"));
        patientSurname.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientSurname"));
        patientCurrentDiagnosis.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientCurrentDiagnosis"));
        patientStatus.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientStatus"));
        patientContact.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientContact"));
        patientAddress.setCellValueFactory(new PropertyValueFactory<PatientData, String>("patientAddress"));

        tblPatients.setRowFactory(new Callback<TableView<PatientData>, TableRow<PatientData>>() {
            @Override
            public TableRow<PatientData> call(TableView<PatientData> tableView) {
                final TableRow<PatientData> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();

                // make Change menu item
                final MenuItem changeMenuItem = new MenuItem("Change");
                changeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // get patient data
                        PatientData data = row.getItem();
                        Patient patient = convertPatientData(data);

                        try {
                            ChangePatient cp = new ChangePatient(hospital, stage, patient);
                            if (cp.refresh()) {
                                System.out.println("Refresh");
                                data = convertPatientData(cp.getPatient());

                                patientData.clear();
                                for (Patient p : hospital.getPatient())
                                    patientData.add(convertPatientData(p));

                                tblPatients.setItems(patientData);
                            }
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // make Remove menu item
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!prompt("Are you sure you would like to remove the selected patient?"))
                            return;

                        // get patient data
                        PatientData data = row.getItem();
                        Patient patient = convertPatientData(data);

                        try {
                            // remove patient from database
                            hospital.removePatient(patient);

                            // remove menu item
                            tblPatients.getItems().remove(row.getItem());
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // add menu items to context menu
                contextMenu.getItems().add(changeMenuItem);
                contextMenu.getItems().add(removeMenuItem);

                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );

                return row ;
            }
        });

        // admissions
        admissionID.setCellValueFactory(new PropertyValueFactory<AdmissionData, String>("admissionID"));
        admissionPatientInfo.setCellValueFactory(new PropertyValueFactory<AdmissionData, String>("admissionPatientInfo"));
        admissionAdmissionDate.setCellValueFactory(new PropertyValueFactory<AdmissionData, String>("admissionAdmissionDate"));
        admissionDischargeDate.setCellValueFactory(new PropertyValueFactory<AdmissionData, String>("admissionDischargeDate"));
        admissionRoomInfo.setCellValueFactory(new PropertyValueFactory<AdmissionData, String>("admissionRoomInfo"));
        admissionStaffInfo.setCellValueFactory(new PropertyValueFactory<AdmissionData, String>("admissionStaffInfo"));

        tblAdmissions.setRowFactory(new Callback<TableView<AdmissionData>, TableRow<AdmissionData>>() {
            @Override
            public TableRow<AdmissionData> call(TableView<AdmissionData> tableView) {
                final TableRow<AdmissionData> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();

                // make Change menu item
                final MenuItem changeMenuItem = new MenuItem("Change");
                changeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // get admission data
                        AdmissionData data = row.getItem();
                        Admission admission = convertAdmissionData(data);

                        try {
                            ChangeAdmission ca = new ChangeAdmission(hospital, stage, admission);
                            if (ca.refresh()) {
                                System.out.println("Refresh");
                                data = convertAdmissionData(ca.getAdmission());

                                admissionData.clear();
                                for (Admission a : hospital.getAdmission())
                                    admissionData.add(convertAdmissionData(a));

                                tblAdmissions.setItems(admissionData);

                                patientData.clear();
                                for (Patient p : hospital.getPatient())
                                    patientData.add(convertPatientData(p));

                                tblPatients.setItems(patientData);
                                updateProgressIndicators();
                                updateRoomTable();
                            }
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // make Remove menu item
                final MenuItem removeMenuItem = new MenuItem("Remove");
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!prompt("Are you sure you would like to remove the selected admission?"))
                            return;

                        // get admission data
                        AdmissionData data = row.getItem();
                        Admission admission = convertAdmissionData(data);

                        try {
                            // remove admission from database
                            hospital.removeAdmission(admission);

                            // remove menu item
                            tblAdmissions.getItems().remove(row.getItem());
                            updateProgressIndicators();
                            updateRoomTable();
                        } catch (Exception e) { System.out.println(e.getMessage()); }
                    }
                });

                // add menu items to context menu
                contextMenu.getItems().add(changeMenuItem);
                contextMenu.getItems().add(removeMenuItem);

                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );

                return row ;
            }
        });
    }

    public void run(Hospital hospital, Stage primaryStage) {
        this.hospital = hospital;
        stage = primaryStage;

        try {
            // load UI from fxml and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls

            ArrayList<Room> rooms = this.hospital.getRooms();
            ArrayList<Staff> staff_list = this.hospital.getStaff();
            ArrayList<Patient> patient_list = this.hospital.getPatient();
            ArrayList<Admission> admissions = this.hospital.getAdmission();
            
            // rooms
            updateRoomTable();

            // staff
            for (Staff staff : staff_list)
                staffData.add(convertStaffData(staff));
            tblStaff.setItems(staffData);

            // patients
            for (Patient patient : patient_list)
                patientData.add(convertPatientData(patient));
            tblPatients.setItems(patientData);

            // admissions
            for (Admission admission : admissions)
                admissionData.add(convertAdmissionData(admission));
            tblAdmissions.setItems(admissionData);

            // progress indicators
            updateProgressIndicators();

            // current user
            lblCurrentUser.setText("User: " + this.hospital.getCurrentUser());

            // configure stage and show it
            stage.setMinWidth(900);
            stage.setMinHeight(550);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateProgressIndicators() {
        ArrayList<Staff> staff_list = this.hospital.getStaff();
        ArrayList<Room> rooms = this.hospital.getRooms();
        ArrayList<Admission> admissions = this.hospital.getAdmission();

        double staff_total = staff_list.size();
        double staff_on_duty = 0;

        for (Staff staff : staff_list)
            if (staff.status.equals("On-duty"))
                staff_on_duty++;

        if (staff_total > 0)
            prgStaff.setProgress(staff_on_duty/staff_total);
        else
            prgStaff.setProgress(0.0);

        txtStaffStatus.setText(Math.round(staff_on_duty) + " of " + Math.round(staff_total) + " Staff on Duty");

        double rooms_total = 0;
        double patients_admitted = 0;

        for (Room room : rooms)
            rooms_total += room.capacity;

        for (Admission admission : admissions)
            if (admission.dischargeDate.isEmpty())
                patients_admitted++;

        if (rooms_total > 0)
            prgPatients.setProgress(patients_admitted / rooms_total);
        else
            prgPatients.setProgress(0.0);

        txtPatientsStatus.setText(Math.round(patients_admitted) + " of " + Math.round(rooms_total) + " Patients Admitted");
    }

    private void updateRoomTable() {
        roomData.clear();
        ArrayList<Room> rooms = this.hospital.getRooms();
        ArrayList<Admission> admissions = this.hospital.getAdmission();
        for (Room room : rooms) {
            RoomData data = convertRoomData(room);

            // figure out room status
            int admission_count = 0;
            for (Admission admission : admissions)
                if (admission.roomID.equals(Integer.toString(room.roomNumber)) && admission.dischargeDate.isEmpty())
                    admission_count++;

            if (admission_count == 0)
                data.setRoomStatus("Empty");
            else
            if (admission_count >= room.capacity)
                data.setRoomStatus("Full");
            else
                data.setRoomStatus((room.capacity - admission_count) + " slots available");

            roomData.add(data);
        }
        tblRooms.setItems(roomData);
    }

    @FXML private void onAddMedicine() {
        System.out.println("onAddMedicine()");
    }

    @FXML private void onAddStaff() {
        AddStaff as = new AddStaff(hospital, stage);
        if (as.refresh()) {
            System.out.println("Refresh");
            staffData.add(convertStaffData(as.getStaff()));
            updateProgressIndicators();
        }
    }

    @FXML private void onAddRoom() {
        AddRoom ar = new AddRoom(hospital, stage);
        if (ar.refresh()) {
            System.out.println("Refresh");
            updateRoomTable();
            updateProgressIndicators();
        }
    }

    @FXML private void onAddPatient() {
        AddPatient ar = new AddPatient(hospital, stage);
        if (ar.refresh()) {
            System.out.println("Refresh");
            patientData.add(convertPatientData(ar.getPatient()));
        }
    }

    @FXML private void onAddAdmission() {
        AddAdmission ar = new AddAdmission(hospital, stage);
        if (ar.refresh()) {
            System.out.println("Refresh");
            admissionData.add(convertAdmissionData(ar.getAdmission()));

            patientData.clear();
            for (Patient p : hospital.getPatient())
                patientData.add(convertPatientData(p));
            tblPatients.setItems(patientData);
            updateProgressIndicators();
            updateRoomTable();
        }
    }

    // conversion method for rooms
    
    public Room convertRoomData(RoomData data) {
        Room room = new Room();
        room.roomNumber = Integer.parseInt(data.getRoomID());
        room.capacity = Integer.parseInt(data.getRoomCapacity());
        room.description = data.getRoomDescription();
        return room;
    }

    public RoomData convertRoomData(Room room) {
        RoomData data = new RoomData();
        data.setRoomID(Integer.toString(room.roomNumber));
        data.setRoomDescription(room.description);
        data.setRoomCapacity(Integer.toString(room.capacity));
        return data;
    }
    
    // conversion methods for staff

    public Staff convertStaffData(StaffData data) {
        Staff staff = new Staff();
        staff.staffID = data.getStaffID();
        staff.title = data.getStaffTitle();
        staff.firstName = data.getStaffFirstName();
        staff.surname = data.getStaffSurname();
        staff.position = data.getStaffPosition();
        staff.status = data.getStaffStatus();
        staff.contact = data.getStaffContact();
        staff.address = data.getStaffAddress();
        return staff;
    }

    public StaffData convertStaffData(Staff staff) {
        StaffData data = new StaffData();
        data.setStaffID(staff.staffID);
        data.setStaffTitle(staff.title);
        data.setStaffFirstName(staff.firstName);
        data.setStaffSurname(staff.surname);
        data.setStaffPosition(staff.position);
        data.setStaffStatus(staff.status);
        data.setStaffContact(staff.contact);
        data.setStaffAddress(staff.address);
        return data;
    }
    
    // conversion methods for patients

    public Patient convertPatientData(PatientData data) {
        Patient patient = new Patient();
        patient.patientID = data.getPatientID();
        patient.firstName = data.getPatientFirstName();
        patient.surname = data.getPatientSurname();
        patient.currentDiagnosis = data.getPatientCurrentDiagnosis();
        patient.contact = data.getPatientContact();
        patient.address = data.getPatientAddress();
        return patient;
    }

    public PatientData convertPatientData(Patient patient) {
        PatientData data = new PatientData();
        data.setPatientID(patient.patientID);
        data.setPatientFirstName(patient.firstName);
        data.setPatientSurname(patient.surname);
        data.setPatientCurrentDiagnosis(patient.currentDiagnosis);
        data.setPatientStatus("");
        data.setPatientContact(patient.contact);
        data.setPatientAddress(patient.address);
        return data;
    }

    // conversion methods for patients

    public Admission convertAdmissionData(AdmissionData data) {
        Admission admission = new Admission();
        admission.admissionID = data.getAdmissionID();
        admission.admissionDate = data.getAdmissionAdmissionDate();
        admission.dischargeDate = data.getAdmissionDischargeDate();
        admission.patientID = extractID(data.getAdmissionPatientInfo());
        admission.roomID = extractID(data.getAdmissionRoomInfo());
        admission.staffID = extractID(data.getAdmissionStaffInfo());
        return admission;
    }

    public AdmissionData convertAdmissionData(Admission admission) {
        AdmissionData data = new AdmissionData();

        try {
            data.setAdmissionID(admission.admissionID);
            data.setAdmissionAdmissionDate(admission.admissionDate);
            data.setAdmissionDischargeDate(admission.dischargeDate);

            Patient patient = this.hospital.getPatient(admission.patientID);
            data.setAdmissionPatientInfo(patient.firstName + " " + patient.surname + " (" + patient.patientID + ")");

            Room room = this.hospital.getRoom(admission.roomID);
            data.setAdmissionRoomInfo("Room (" + room.roomNumber + "): Max " + room.capacity + ", Usage: " + room.description);

            Staff staff = this.hospital.getStaff(admission.staffID);
            data.setAdmissionStaffInfo(staff.title + " " + staff.firstName + " " + staff.surname + "[" + staff.position + "] (" + staff.staffID + ")");

        } catch (Exception e) { System.out.println(e.getMessage()); }

        return data;
    }

    private String extractID(String s) {
        s = s.substring(s.indexOf("(") + 1);
        s = s.substring(0, s.indexOf(")"));
        return s;
    }
}
