package hosman.dashboard;

import javafx.beans.property.SimpleStringProperty;

// required for TableView to function properly
public class AdmissionData {
    private final SimpleStringProperty admissionID = new SimpleStringProperty();
    private final SimpleStringProperty admissionPatientInfo = new SimpleStringProperty();
    private final SimpleStringProperty admissionAdmissionDate = new SimpleStringProperty();
    private final SimpleStringProperty admissionDischargeDate = new SimpleStringProperty();
    private final SimpleStringProperty admissionRoomInfo = new SimpleStringProperty();
    private final SimpleStringProperty admissionStaffInfo = new SimpleStringProperty();

    public void setAdmissionID(String admissionID) { this.admissionID.set(admissionID); }
    public void setAdmissionPatientInfo(String admissionPatientInfo) { this.admissionPatientInfo.set(admissionPatientInfo); }
    public void setAdmissionAdmissionDate(String admissionAdmissionDate) { this.admissionAdmissionDate.set(admissionAdmissionDate); }
    public void setAdmissionDischargeDate(String admissionDischargeDate) { this.admissionDischargeDate.set(admissionDischargeDate); }
    public void setAdmissionRoomInfo(String admissionRoomInfo) { this.admissionRoomInfo.set(admissionRoomInfo); }
    public void setAdmissionStaffInfo(String admissionStaffInfo) { this.admissionStaffInfo.set(admissionStaffInfo); }

    public String getAdmissionID() { return this.admissionID.get(); }
    public String getAdmissionPatientInfo() { return this.admissionPatientInfo.get(); }
    public String getAdmissionAdmissionDate() { return this.admissionAdmissionDate.get(); }
    public String getAdmissionDischargeDate() { return this.admissionDischargeDate.get(); }
    public String getAdmissionRoomInfo() { return this.admissionRoomInfo.get(); }
    public String getAdmissionStaffInfo() { return this.admissionStaffInfo.get(); }
}