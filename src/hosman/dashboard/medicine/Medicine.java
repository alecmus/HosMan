package hosman.dashboard.medicine;

import hosman.Database;

class MedicineInfo{
    public String medID;
    public String name;
    public String description;
    public int quantity = 0;
}

public class Medicine {
    private Database db;

    public Medicine(Database db) {
        this.db = db;
    }

    void addMedicine(MedicineInfo info) {

    }
}
