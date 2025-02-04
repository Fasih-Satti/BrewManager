package com.example.brewmanager;
public class order {
    private String chefName;
    private int timeInMinutes;

    public order() {
        // Default constructor required for Firebase
    }

    public order(String chefName, int timeInMinutes) {
        this.chefName = chefName;
        this.timeInMinutes = timeInMinutes;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
