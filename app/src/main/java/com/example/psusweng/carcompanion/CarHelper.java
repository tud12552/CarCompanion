package com.example.psusweng.carcompanion;

public class CarHelper {

    int EstYearlyMiles, Year;
    Double Mileage;
    String Make, Model, LastOilChange;

    public CarHelper() {

    }

    public CarHelper(String LastOilChange, int Year, int EstYearlyMiles, Double Mileage, String Model, String Make) {
        this.EstYearlyMiles = EstYearlyMiles;
        this.Year = Year;
        this.Mileage = Mileage;
        this.Make = Make;
        this.Model = Model;
        this.LastOilChange = LastOilChange;
    }

    public CarHelper(int year, String make, String model, Double miles)
    {
        this.Mileage = miles;
        this.Model = model;
        this.Make = make;
    }
}