package hosman.dashboard;

import javafx.beans.property.SimpleStringProperty;

// required for TableView to function properly
public class StaffData {
    private final SimpleStringProperty staffID = new SimpleStringProperty();
    private final SimpleStringProperty staffTitle = new SimpleStringProperty();
    private final SimpleStringProperty staffFirstName = new SimpleStringProperty();
    private final SimpleStringProperty staffSurname = new SimpleStringProperty();
    private final SimpleStringProperty staffPosition = new SimpleStringProperty();
    private final SimpleStringProperty staffStatus = new SimpleStringProperty();
    private final SimpleStringProperty staffContact = new SimpleStringProperty();
    private final SimpleStringProperty staffAddress = new SimpleStringProperty();

    public void setStaffID(String staffID) { this.staffID.set(staffID); }
    public void setStaffTitle(String staffTitle) { this.staffTitle.set(staffTitle); }
    public void setStaffFirstName(String staffFirstName) { this.staffFirstName.set(staffFirstName); }
    public void setStaffSurname(String staffSurname) { this.staffSurname.set(staffSurname); }
    public void setStaffPosition(String staffPosition) { this.staffPosition.set(staffPosition); }
    public void setStaffStatus(String staffStatus) { this.staffStatus.set(staffStatus); }
    public void setStaffContact(String staffContact) { this.staffContact.set(staffContact); }
    public void setStaffAddress(String staffAddress) { this.staffAddress.set(staffAddress); }

    public String getStaffID() { return this.staffID.get(); }
    public String getStaffTitle() { return this.staffTitle.get(); }
    public String getStaffFirstName() { return this.staffFirstName.get(); }
    public String getStaffSurname() { return this.staffSurname.get(); }
    public String getStaffPosition() { return this.staffPosition.get(); }
    public String getStaffStatus() { return this.staffStatus.get(); }
    public String getStaffContact() { return this.staffContact.get(); }
    public String getStaffAddress() { return this.staffAddress.get(); }
}