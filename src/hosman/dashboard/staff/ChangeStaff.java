package hosman.dashboard.staff;

import hosman.Staff;
import hosman.Hospital;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class ChangeStaff {
    private static final String title = "HosMan Change Staff";
    Hospital hospital;
    Stage stage = new Stage();
    private boolean staffChanged = false;
    private Staff staff;

    @FXML private TextField txtStaffID, txtFirstName, txtSurname, txtContact, txtAddress;
    @FXML private ComboBox<String> cmbTitle, cmbPosition, cmbStatus;

    public void alert(String message) {
        // display message box
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public ChangeStaff(Hospital hospital, Stage owner, Staff staff) {
        this.hospital = hospital;

        try {
            // load UI from fxml and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeStaff.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls
            cmbTitle.getItems().addAll("Mr.", "Ms.", "Miss.", "Mrs.", "Dr.");
            cmbPosition.getItems().addAll("Doctor", "Nurse", "General Hand", "Midwife", "Sister in Charge");
            cmbStatus.getItems().addAll("On-duty", "On-standby", "On-leave", "Unavailable");

            txtStaffID.setText(staff.staffID);
            cmbTitle.setValue(staff.title);
            txtFirstName.setText(staff.firstName);
            txtSurname.setText(staff.surname);
            cmbPosition.setValue(staff.position);
            cmbStatus.setValue(staff.status);
            txtContact.setText(staff.contact);
            txtAddress.setText(staff.address);

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

        // get the staff details
        staff = new Staff();
        staff.staffID = txtStaffID.getText();
        staff.title = cmbTitle.getValue();
        staff.firstName = txtFirstName.getText();
        staff.surname = txtSurname.getText();
        staff.position = cmbPosition.getValue();
        staff.status = cmbStatus.getValue();
        staff.contact = txtContact.getText();
        staff.address = txtAddress.getText();

        try {
            hospital.changeStaff(staff);
            staffChanged = true;
            stage.close();
        }
        catch (Exception e){
            alert(e.getMessage());
        }
    }

    public boolean refresh() { return staffChanged; }
    public Staff getStaff() { return staff; }
}
