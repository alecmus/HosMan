package hosman.dashboard.rooms;

import hosman.Hospital;
import hosman.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.lang.Integer;

public class AddRoom {
    private static final String title = "Add Room";
    Hospital hospital;
    Stage stage = new Stage();
    private boolean roomAdded = false;
    private Room room;

    @FXML private TextField txtRoomID;
    @FXML private ComboBox<String> cmbDescription;
    @FXML private ComboBox<String> cmbCapacity;

    public void alert(String message) {
        // display message box
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public AddRoom(Hospital hospital, Stage owner) {
        this.hospital = hospital;

        try {
            // load UI from fxml and create a scene from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRoom.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());

            // initialize some controls
            cmbDescription.getItems().addAll("General", "Maternity", "Children", "Intensive Care");
            cmbDescription.setValue("General");

            cmbCapacity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            cmbCapacity.setValue("1");

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

        // get the room details
        String roomID = txtRoomID.getText();
        String roomDescription = cmbDescription.getValue();
        String roomCapacity = cmbCapacity.getValue();

        room = new Room();
        room.roomNumber = Integer.parseInt(roomID);
        room.description = roomDescription;
        room.capacity = Integer.parseInt(roomCapacity);

        try {
            hospital.newRoom(room);
            roomAdded = true;
            stage.close();
        }
        catch (Exception e){
            alert(e.getMessage());
        }
    }

    public boolean refresh() { return roomAdded; }

    public Room getRoom() { return room; }
}
