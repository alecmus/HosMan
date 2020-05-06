package hosman.dashboard.rooms;

import hosman.Admission;
import hosman.Hospital;
import hosman.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.lang.Integer;
import java.util.ArrayList;

public class ChangeRoom {
    private static final String title = new String("HosMan Change Room");
    Hospital hospital;
    Stage stage = new Stage();
    private boolean roomChanged = false;
    private Room room;

    @FXML private TextField txtRoomID;
    @FXML private ComboBox<String> cmbDescription;
    @FXML private ComboBox<String> cmbCapacity;
    @FXML private Label lblRoomStatus;
    @FXML private Button btnSave;

    public void alert(Alert.AlertType type, String header, String contextText) {
        // display message box
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public ChangeRoom(Hospital hospital, Stage owner, Room room) {
        this.hospital = hospital;
        this.room = room;

        try {
            // load UI from fxml and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeRoom.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls
            txtRoomID.setText(Integer.toString(this.room.roomNumber));
            cmbDescription.getItems().addAll("General", "Maternity", "Children", "Intensive Care");
            cmbDescription.setValue(room.description);

            cmbCapacity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            cmbCapacity.setValue(Integer.toString(this.room.capacity));

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

        // to-do: prevent reducing room number lower than current patient number in room

        // get the room details
        String roomID = txtRoomID.getText();
        String roomDescription = cmbDescription.getValue();
        String roomCapacity = cmbCapacity.getValue();

        room = new Room();
        room.roomNumber = Integer.parseInt(roomID);
        room.description = roomDescription;
        room.capacity = Integer.parseInt(roomCapacity);

        try {
            hospital.changeRoom(room);
            roomChanged = true;
            stage.close();
        }
        catch (Exception e){
            alert(Alert.AlertType.WARNING, "Saving Failed", e.getMessage());
        }
    }

    @FXML void onRoom() {
        int attempted_capacity = Integer.parseInt(cmbCapacity.getValue());
        int admission_count = 0;

        boolean allow = false;
        try {
            Room room = this.hospital.getRoom(txtRoomID.getText());
            ArrayList<Admission> admissions = this.hospital.getAdmission();

            // figure out room status
            for (Admission admission : admissions)
                if (admission.roomID.equals(Integer.toString(room.roomNumber)) && admission.dischargeDate.isEmpty())
                    admission_count++;

            if (attempted_capacity >= admission_count)
                allow = true;
        } catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            lblRoomStatus.setText("Miminum permissible size = " + admission_count);
            if (allow)
                btnSave.setDisable(false);
            else
                btnSave.setDisable(true);
        }
    }

    public boolean refresh() { return roomChanged; }
    public Room getRoom() { return room; }
}
