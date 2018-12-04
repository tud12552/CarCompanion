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
    private Double DistanceTotal;  // PGN 65248, SPN 245, 0.125 km per bit
    private Double FuelTotal;  // PGN 65253, SPN 247, 0.5 liters per bit
    private Double HoursTotal;  // PGN 65257, SPN 250, 0.05 hours per bit

    private static Double SPN_245_CONV = 0.125;
    private static Double SPN_250_CONV = 0.05;
    private static Double SPN_247_CONV = 0.5;

    public J1939Data() {}
    public J1939Data(Double distance, Double fuel, Double hours) {
        this.DistanceTotal = distance;
        this.FuelTotal = fuel;
        this.HoursTotal = hours;
    }

    public Double getDistanceTotal() {  // In kilometers
        return DistanceTotal * SPN_245_CONV;
    }

    public Double getFuelTotal() {
        return FuelTotal * SPN_250_CONV;
    }

    public Double getHoursTotal() {
        return HoursTotal * SPN_247_CONV;
    }

}
