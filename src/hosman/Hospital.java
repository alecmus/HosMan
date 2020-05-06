package hosman;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Hospital implements HospitalInterface {
    private Database db;
    private String currentUser;

    public Hospital(Database db) { this.db = db; }
    
    // rooms

    @Override
    public void newRoom(Room room) throws Exception {
        final String sql = "INSERT INTO Rooms(RoomID, Description, Capacity) VALUES('" +
                room.roomNumber + "', '" + room.description + "', " + room.capacity + ")";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            final String sql = "SELECT * FROM Rooms;";
            ResultSet rs = db.conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Room room = new Room();
                room.roomNumber = Integer.parseInt(rs.getString("RoomID"));
                room.description = rs.getString("Description");
                room.capacity = rs.getInt("Capacity");

                // to-do: get room status from Admissions table

                rooms.add(room);
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }

        return rooms;
    }

    public Room getRoom(String roomID) throws Exception {
        Room room = new Room();

        final String sql = "SELECT * FROM Rooms WHERE RoomID = '" + roomID + "';";
        ResultSet rs = db.conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            room.roomNumber = Integer.parseInt(rs.getString("RoomID"));
            room.description = rs.getString("Description");
            room.capacity = rs.getInt("Capacity");
        }

        return room;
    }

    @Override
    public void changeRoom(Room room) throws Exception {
        final String sql = "UPDATE Rooms SET Description = '" +
                room.description + "', Capacity = '" + room.capacity + "' WHERE RoomID = '" + room.roomNumber + "';";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public void removeRoom(Room room) throws Exception {
        final String sql = "DELETE FROM Rooms WHERE RoomID = '" + room.roomNumber + "';";
        db.conn.createStatement().execute(sql);
    }
    
    // staff

    @Override
    public void newStaff(Staff staff) throws Exception {
        final String sql = "INSERT INTO Staff(StaffID, Title, FirstName, Surname, Position, Status, Contact, Address) VALUES('" +
                staff.staffID + "', '" + staff.title + "', '" + staff.firstName + "', '" +
                staff.surname + "', '" + staff.position + "', '" + staff.status + "', '" +
                staff.contact + "', '" + staff.address + "')";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public ArrayList<Staff> getStaff() {
        ArrayList<Staff> staff_list = new ArrayList<>();

        try {
            final String sql = "SELECT * FROM Staff;";
            ResultSet rs = db.conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Staff staff = new Staff();
                staff.staffID = rs.getString("StaffID");
                staff.title = rs.getString("Title");
                staff.firstName = rs.getString("FirstName");
                staff.surname = rs.getString("Surname");
                staff.position = rs.getString("Position");
                staff.status = rs.getString("Status");
                staff.contact = rs.getString("Contact");
                staff.address = rs.getString("Address");
                staff_list.add(staff);
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }

        return staff_list;
    }

    public Staff getStaff(String staffID) throws Exception {
        Staff staff = new Staff();

        final String sql = "SELECT * FROM Staff WHERE StaffID = '" + staffID + "';";
        ResultSet rs = db.conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            staff.staffID = rs.getString("StaffID");
            staff.title = rs.getString("Title");
            staff.firstName = rs.getString("FirstName");
            staff.surname = rs.getString("Surname");
            staff.position = rs.getString("Position");
            staff.status = rs.getString("Status");
            staff.contact = rs.getString("Contact");
            staff.address = rs.getString("Address");
        }

        return staff;
    }

    @Override
    public void changeStaff(Staff staff) throws Exception {
        final String sql = "UPDATE Staff SET Title = '" +
                staff.title + "', FirstName = '" + staff.firstName + "', Surname = '" +
                staff.surname + "', Position = '" + staff.position + "', Status = '" +
                staff.status + "', Contact = '" + staff.contact + "', Address = '" +
                staff.address + "' WHERE StaffID = '" + staff.staffID + "';";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public void removeStaff(Staff staff) throws Exception {
        final String sql = "DELETE FROM Staff WHERE StaffID = '" + staff.staffID + "';";
        db.conn.createStatement().execute(sql);
    }

    // patient

    @Override
    public void newPatient(Patient patient) throws Exception {
        final String sql = "INSERT INTO Patients(PatientID, FirstName, Surname, CurrentDiagnosis, Contact, Address) VALUES('" +
                patient.patientID + "', '" + patient.firstName + "', '" +
                patient.surname + "', '" + patient.currentDiagnosis + "', '" +
                patient.contact + "', '" + patient.address + "')";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public ArrayList<Patient> getPatient() {
        ArrayList<Patient> patient_list = new ArrayList<>();

        try {
            final String sql = "SELECT * FROM Patients;";
            ResultSet rs = db.conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Patient patient = new Patient();
                patient.patientID = rs.getString("PatientID");
                patient.firstName = rs.getString("FirstName");
                patient.surname = rs.getString("Surname");
                patient.currentDiagnosis = rs.getString("CurrentDiagnosis");
                patient.contact = rs.getString("Contact");
                patient.address = rs.getString("Address");
                patient_list.add(patient);
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }

        return patient_list;
    }

    public Patient getPatient(String patientID) throws Exception {
        Patient patient = new Patient();

        final String sql = "SELECT * FROM Patients WHERE PatientID = '" + patientID + "';";
        ResultSet rs = db.conn.createStatement().executeQuery(sql);
        if (rs.next()) {
            patient.patientID = rs.getString("PatientID");
            patient.firstName = rs.getString("FirstName");
            patient.surname = rs.getString("Surname");
            patient.currentDiagnosis = rs.getString("CurrentDiagnosis");
            patient.contact = rs.getString("Contact");
            patient.address = rs.getString("Address");
        }

        return patient;
    }

    @Override
    public void changePatient(Patient patient) throws Exception {
        final String sql = "UPDATE Patients SET FirstName = '" +
                patient.firstName + "', Surname = '" +
                patient.surname + "', CurrentDiagnosis = '" + patient.currentDiagnosis +
                "', Contact = '" + patient.contact + "', Address = '" +
                patient.address + "' WHERE PatientID = '" + patient.patientID + "';";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public void removePatient(Patient patient) throws Exception {
        final String sql = "DELETE FROM Patients WHERE PatientID = '" + patient.patientID + "';";
        db.conn.createStatement().execute(sql);
    }

    // admission

    @Override
    public void newAdmission(Admission admission) throws Exception {
        final String sql = "INSERT INTO Admissions(AdmissionID, PatientID, Admitted, Discharged, RoomID, StaffID) VALUES('" +
                admission.admissionID + "', '" + admission.patientID + "', '" +
                admission.admissionDate + "', '" + admission.dischargeDate + "', '" +
                admission.roomID + "', '" + admission.staffID + "')";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public ArrayList<Admission> getAdmission() {
        ArrayList<Admission> admission_list = new ArrayList<>();

        try {
            final String sql = "SELECT * FROM Admissions;";
            ResultSet rs = db.conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Admission admission = new Admission();
                admission.admissionID = rs.getString("AdmissionID");
                admission.patientID = rs.getString("PatientID");
                admission.admissionDate = rs.getString("Admitted");
                admission.dischargeDate = rs.getString("Discharged");
                admission.roomID = rs.getString("RoomID");
                admission.staffID = rs.getString("StaffID");
                admission_list.add(admission);
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }

        return admission_list;
    }

    @Override
    public void changeAdmission(Admission admission) throws Exception {
        final String sql = "UPDATE Admissions SET Discharged = '" +
                admission.dischargeDate + "', RoomID = '" +
                admission.roomID + "' WHERE AdmissionID = '" + admission.admissionID + "';";
        db.conn.createStatement().execute(sql);
    }

    @Override
    public void removeAdmission(Admission admission) throws Exception {
        final String sql = "DELETE FROM Admissions WHERE AdmissionID = '" + admission.admissionID + "';";
        db.conn.createStatement().execute(sql);
    }

    public void setCurrentUser(String currentUser) { this.currentUser = currentUser; }
    public String getCurrentUser() { return this.currentUser; }
}
