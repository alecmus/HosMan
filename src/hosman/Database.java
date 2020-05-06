package hosman;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    Connection conn = null;

    public void close() {
        try {
            if (conn != null) conn.close();
        }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void connect(String fileName, String password) throws Exception {
        if (conn != null) return;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);    // create a connection to the database

            // create tables

            // create Users table
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Users ("
                    + "Username TEXT PRIMARY KEY NOT NULL, "
                    + "Password TEXT NOT NULL"
                    + ");");

            // create Admissions table
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Admissions ("
                            + "AdmissionID TEXT PRIMARY KEY NOT NULL, "
                            + "PatientID TEXT NOT NULL, "
                            + "Admitted TEXT NOT NULL, "
                            + "Discharged TEXT, "
                            + "RoomID TEXT NOT NULL, "
                            + "StaffID TEXT"
                            + ");");

            // create Patients table
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Patients ("
                            + "PatientID TEXT PRIMARY KEY NOT NULL, "
                            + "FirstName TEXT NOT NULL, "
                            + "Surname TEXT, "
                            + "CurrentDiagnosis TEXT, "
                            + "Contact TEXT, "
                            + "Address TEXT"
                            + ");");

            // create Rooms table
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Rooms ("
                            + "RoomID TEXT PRIMARY KEY NOT NULL, "
                            + "Description TEXT, "
                            + "Capacity INTEGER"
                            + ");");

            // create Staff table
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Staff ("
                            + "StaffID TEXT PRIMARY KEY NOT NULL, "
                            + "Title TEXT, "
                            + "FirstName TEXT NOT NULL, "
                            + "Surname TEXT, "
                            + "Position TEXT, "
                            + "Status TEXT, "
                            + "Contact TEXT, "
                            + "Address TEXT"
                            + ");");

            // create Medicine table
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS Medicine ("
                            + "MedicineID TEXT PRIMARY KEY NOT NULL, "
                            + "Name TEXT NOT NULL, "
                            + "Description TEXT, "
                            + "Units TEXT, "
                            + "Quantity REAL"
                            + ");");
        }
        catch (SQLException e) { throw new Exception(e.getMessage()); }
    }

    public boolean connected() { return conn != null; }

    public boolean hasUsers() {
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Users;");
            if (rs.next()) return true;
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
        return false;
    }

    public void newUser(String username, String password) {
        try {
            conn.createStatement().execute("INSERT INTO Users(Username, Password) VALUES('" + username + "', '" + password + "')");
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public boolean checkCredentials(String username, String password) {
        try {
            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT * FROM Users WHERE Username = '" + username + "' AND Password = '" + password + "';");
            if (rs.next()) return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
