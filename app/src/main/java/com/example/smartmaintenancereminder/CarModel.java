package com.example.smartmaintenancereminder;

public class CarModel {

    String id;
    String carName;
    String plateNumber;
    String maintenanceType;
    String lastMaintenance;
    String nextMaintenance;
    String notes;

    // Empty constructor (required for Firebase)
    public CarModel() {
    }

    // Full constructor
    public CarModel(String id, String carName, String plateNumber, String maintenanceType,
                    String lastMaintenance, String nextMaintenance, String notes) {

        this.id = id;
        this.carName = carName;
        this.plateNumber = plateNumber;
        this.maintenanceType = maintenanceType;
        this.lastMaintenance = lastMaintenance;
        this.nextMaintenance = nextMaintenance;
        this.notes = notes;
    }

    // Getters
    public String getId() { return id; }
    public String getCarName() { return carName; }
    public String getPlateNumber() { return plateNumber; }
    public String getMaintenanceType() { return maintenanceType; }
    public String getLastMaintenance() { return lastMaintenance; }
    public String getNextMaintenance() { return nextMaintenance; }
    public String getNotes() { return notes; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setCarName(String carName) { this.carName = carName; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public void setMaintenanceType(String maintenanceType) { this.maintenanceType = maintenanceType; }
    public void setLastMaintenance(String lastMaintenance) { this.lastMaintenance = lastMaintenance; }
    public void setNextMaintenance(String nextMaintenance) { this.nextMaintenance = nextMaintenance; }
    public void setNotes(String notes) { this.notes = notes; }
}