package hosman.dashboard;

import javafx.beans.property.SimpleStringProperty;

// required for TableView to function properly
public class PatientData {
    private final SimpleStringProperty patientID = new SimpleStringProperty();
    private final SimpleStringProperty patientFirstName = new SimpleStringProperty();
    private final SimpleStringProperty patientSurname = new SimpleStringProperty();
    private final SimpleStringProperty patientCurrentDiagnosis = new SimpleStringProperty();
    private final SimpleStringProperty patientStatus = new SimpleStringProperty();
    private final SimpleStringProperty patientContact = new SimpleStringProperty();
    private final SimpleStringProperty patientAddress = new SimpleStringProperty();

    public void setPatientID(String patientID) { this.patientID.set(patientID); }
    public void setPatientFirstName(String patientFirstName) { this.patientFirstName.set(patientFirstName); }
    public void setPatientSurname(String patientSurname) { this.patientSurname.set(patientSurname); }
    public void setPatientCurrentDiagnosis(String patientCurrentDiagnosis) { this.patientCurrentDiagnosis.set(patientCurrentDiagnosis); }
    public void setPatientStatus(String patientStatus) { this.patientStatus.set(patientStatus); }
    public void setPatientContact(String patientContact) { this.patientContact.set(patientContact); }
    public void setPatientAddress(String patientAddress) { this.patientAddress.set(patientAddress); }

    public String getPatientID() { return this.patientID.get(); }
    public String getPatientFirstName() { return this.patientFirstName.get(); }
    public String getPatientSurname() { return this.patientSurname.get(); }
    public String getPatientCurrentDiagnosis() { return this.patientCurrentDiagnosis.get(); }
    public String getPatientStatus() { return this.patientStatus.get(); }
    public String getPatientContact() { return this.patientContact.get(); }
    public String getPatientAddress() { return this.patientAddress.get(); }
}