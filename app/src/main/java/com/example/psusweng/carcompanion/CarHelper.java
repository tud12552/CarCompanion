package com.example.psusweng.carcompanion;

public class CarHelper {

    int EstYearlyMiles, Year, Mileage;
    String Make, Model, LastOilChange;

    public CarHelper() {

    }

    public CarHelper(String LastOilChange, int Year, int EstYearlyMiles, int Mileage, String Model, String Make) {
        this.EstYearlyMiles = EstYearlyMiles;
        this.Year = Year;
        this.Mileage = Mileage;
        this.Make = Make;
        this.Model = Model;
        this.LastOilChange = LastOilChange;
    }

    public CarHelper(int year, String make, String model, int miles)
    {
        this.Mileage = miles;
        this.Model = model;
        this.Make = make;
    }
}