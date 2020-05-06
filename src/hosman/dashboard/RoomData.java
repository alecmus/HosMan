package hosman.dashboard;

import javafx.beans.property.SimpleStringProperty;

// required for TableView to function properly
public class RoomData {
    private final SimpleStringProperty roomID = new SimpleStringProperty();
    private final SimpleStringProperty roomDescription = new SimpleStringProperty();
    private final SimpleStringProperty roomCapacity = new SimpleStringProperty();
    private final SimpleStringProperty roomStatus = new SimpleStringProperty();

    public void setRoomID(String roomID) { this.roomID.set(roomID); }
    public void setRoomDescription(String roomDescription) { this.roomDescription.set(roomDescription); }
    public void setRoomCapacity(String roomCapacity) { this.roomCapacity.set(roomCapacity); }
    public void setRoomStatus(String roomStatus) { this.roomStatus.set(roomStatus); }

    public String getRoomID() { return this.roomID.get(); }
    public String getRoomDescription() { return this.roomDescription.get(); }
    public String getRoomCapacity() { return this.roomCapacity.get(); }
    public String getRoomStatus() { return this.roomStatus.get(); }
}