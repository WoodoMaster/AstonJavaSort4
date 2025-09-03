package org.aston.learning.stage1.model;

public class Bus {
    private String number;
    private String model;
    private int mileage;

    public Bus() {}

    public Bus(String number, String model, int mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

    // Геттеры и сеттеры
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
}
