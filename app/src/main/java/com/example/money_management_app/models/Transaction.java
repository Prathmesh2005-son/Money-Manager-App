package com.example.money_management_app.models;

public class Transaction {

    private String date;
    private final String item;
    private final double amount;
    private final String type; // "+" or "-"

    // ✅ Updated Constructor (with date)
    public Transaction(String item, double amount, String type, String date) {
        this.item = item;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    // Getters
    public String getItem() { return item; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public String getDate() { return date; }

    // Setter (so user can edit date later)
    public void setDate(String date) {
        this.date = date;
    }
}