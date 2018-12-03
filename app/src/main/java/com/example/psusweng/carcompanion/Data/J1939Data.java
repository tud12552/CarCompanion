package com.example.psusweng.carcompanion.Data;

import java.io.Serializable;

// This class encapsulates data expected to be uploaded via
// OBDII/Bluetooth connection.  This is detailed in the
// SAE-J1939/71 specification.  Note that messages are
// identified by "Parameter Group Numbers" (PGN's) while
// each signal available within a message is given a
// "Suspect Parameter Number" (SPN).  All data is broadcast
// in metric units.
public class J1939Data implements Serializable {
    private Double DistanceTotal;  // PGN?, SPN?, may be unique to vendors
    private Double FuelTotal;  // PGN 65257, SPN 250, 0.05 hours per bit
    private Double HoursTotal;  // PGN 65253, SPN 247, 0.5 liters per bit

    public J1939Data() {}
    public J1939Data(Double distance, Double fuel, Double hours) {
        this.DistanceTotal = distance;
        this.FuelTotal = fuel;
        this.HoursTotal = hours;
    }

    public Double getDistanceTotal() {
        return DistanceTotal;
    }

    public void setDistanceTotal(Double distanceTotal) {
        DistanceTotal = distanceTotal;
    }

    public Double getFuelTotal() {
        return FuelTotal;
    }

    public void setFuelTotal(Double fuelTotal) {
        FuelTotal = fuelTotal;
    }

    public Double getHoursTotal() {
        return HoursTotal;
    }

    public void setHoursTotal(Double hoursTotal) {
        HoursTotal = hoursTotal;
    }

}
