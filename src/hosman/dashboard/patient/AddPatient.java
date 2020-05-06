package hosman.dashboard.patient;

import hosman.Patient;
import hosman.Hospital;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class AddPatient {
    private static final String title = "HosMan Add Patient";
    Hospital hospital;
    Stage stage = new Stage();
    private boolean patientAdded = false;
    private Patient patient;

    @FXML private TextField txtPatientID, txtFirstName, txtSurname, txtContact, txtAddress;
    @FXML private ComboBox<String> cmbCurrentDiagnosis;

    public void alert(String message) {
        // display message box
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public AddPatient(Hospital hospital, Stage owner) {
        this.hospital = hospital;

        try {
            // load UI from fxml and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPatient.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls
            cmbCurrentDiagnosis.getItems().addAll("Measles", "Goitre", "Edema", "Pre-eclampsia", "Migrain", "Diarrhoea");

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

        // get the patient details
        patient = new Patient();
        patient.patientID = txtPatientID.getText();
        patient.firstName = txtFirstName.getText();
        patient.surname = txtSurname.getText();
        patient.currentDiagnosis = cmbCurrentDiagnosis.getValue();
        patient.contact = txtContact.getText();
        patient.address = txtAddress.getText();

        try {
            hospital.newPatient(patient);
            patientAdded = true;
            stage.close();
        }
        catch (Exception e){
            alert(e.getMessage());
        }
    }

    public boolean refresh() { return patientAdded; }
    public Patient getPatient() { return patient; }
}
