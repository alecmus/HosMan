package hosman.dashboard.admissions;

import hosman.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddAdmission {
    private static final String title = "HosMan Add Admission";
    Hospital hospital;
    Stage stage = new Stage();
    private boolean admissionAdded = false;
    private Admission admission;
    private DateConverter converter = new DateConverter();

    @FXML private TextField txtAdmissionID;
    @FXML private DatePicker dtAdmissionDate;
    @FXML private ComboBox<String> cmbPatient, cmbRoom, cmbCurrentDiagnosis, cmbAdmittedBy;
    @FXML private Label txtRoomStatus;
    @FXML private Button btnSave;

    public void alert(String message) {
        // display message box
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public AddAdmission(Hospital hospital, Stage owner) {
        this.hospital = hospital;

        try {
            // load UI from fxml and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAdmission.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls
            dtAdmissionDate.setConverter(converter);

            // patients
            for (Patient patient : this.hospital.getPatient())
                cmbPatient.getItems().add(patient.firstName + " " + patient.surname + " (" + patient.patientID + ")");

            // rooms
            for (Room room : this.hospital.getRooms())
                cmbRoom.getItems().add("Room (" + room.roomNumber + "): Max " + room.capacity + ", Usage: " + room.description);

            // current diagnosis
            cmbCurrentDiagnosis.getItems().addAll("Measles", "Goitre", "Edema", "Pre-eclampsia", "Migrain", "Diarrhoea");

            // staff
            for (Staff staff : this.hospital.getStaff())
                cmbAdmittedBy.getItems().add(staff.title + " " + staff.firstName + " " + staff.surname + "[" + staff.position + "] (" + staff.staffID + ")");

            // configure stage and show it
            stage.setResizable(false);
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onSave() {
        System.out.println("onSave()");

        // get the admission details
        admission = new Admission();
        admission.admissionID = txtAdmissionID.getText();
        admission.admissionDate = converter.toString(dtAdmissionDate.getValue());
        admission.dischargeDate = "";
        admission.patientID = extractID(cmbPatient.getValue());
        admission.roomID = extractID(cmbRoom.getValue());
        admission.staffID = extractID(cmbAdmittedBy.getValue());

        try {
            hospital.newAdmission(admission);
            admissionAdded = true;

            String diagnosis = cmbCurrentDiagnosis.getValue();
            Patient patient = this.hospital.getPatient(admission.patientID);

            if (!diagnosis.equals(patient.currentDiagnosis)) {
                // update patient diagnosis
                patient.currentDiagnosis = diagnosis;
                this.hospital.changePatient(patient);
            }

            stage.close();
        }
        catch (Exception e){
            alert(e.getMessage());
        }
    }

    @FXML public void onPatient() {
        try {
            Patient patient = this.hospital.getPatient(extractID(cmbPatient.getValue()));
            cmbCurrentDiagnosis.setValue(patient.currentDiagnosis);
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    @FXML void onRoom() {
        int slots_available = 0;
        try {
            Room room = this.hospital.getRoom(extractID(cmbRoom.getValue()));
            ArrayList<Admission> admissions = this.hospital.getAdmission();

            // figure out room status
            int admission_count = 0;
            for (Admission admission : admissions)
                if (admission.roomID.equals(Integer.toString(room.roomNumber)) && admission.dischargeDate.isEmpty())
                    admission_count++;

            slots_available = room.capacity - admission_count;
        } catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            txtRoomStatus.setText(slots_available + " slots available");
            if (slots_available > 0)
                btnSave.setDisable(false);
            else
                btnSave.setDisable(true);
        }
    }

    private String extractID(String s) {
        s = s.substring(s.indexOf("(") + 1);
        s = s.substring(0, s.indexOf(")"));
        return s;
    }

    public boolean refresh() { return admissionAdded; }
    public Admission getAdmission() { return admission; }
}
