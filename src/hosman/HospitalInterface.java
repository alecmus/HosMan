package hosman;

import java.util.ArrayList;

public interface HospitalInterface {
    // rooms
    public void newRoom(Room room) throws Exception;
    public ArrayList<Room> getRooms();
    public Room getRoom(String roomID) throws Exception;
    public void changeRoom(Room room) throws Exception;
    public void removeRoom(Room room) throws Exception;

    // staff
    public void newStaff(Staff staff) throws Exception;
    public ArrayList<Staff> getStaff();
    public Staff getStaff(String staffID) throws Exception;
    public void changeStaff(Staff staff) throws Exception;
    public void removeStaff(Staff staff) throws Exception;

    // patient
    public void newPatient(Patient patient) throws Exception;
    public ArrayList<Patient> getPatient();
    public Patient getPatient(String patientID) throws Exception;
    public void changePatient(Patient patient) throws Exception;
    public void removePatient(Patient patient) throws Exception;

    // admission
    public void newAdmission(Admission admission) throws Exception;
    public ArrayList<Admission> getAdmission();
    public void changeAdmission(Admission admission) throws Exception;
    public void removeAdmission(Admission admission) throws Exception;
}