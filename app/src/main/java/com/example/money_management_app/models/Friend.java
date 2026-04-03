package com.example.money_management_app.models;

import java.util.ArrayList;

public class Friend {
    private final String name;
    private double total;
    private final ArrayList<Transaction> transactions;
    private boolean expanded = false; // for UI expand/collapse

    public Friend(String name) {
        this.name = name;
        this.total = 0;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        if ("+".equals(t.getType())) total += t.getAmount();
        else total -= t.getAmount();
    }

    public String getName() { return name; }
    public double getTotal() { return total; }
    public ArrayList<Transaction> getTransactions() { return transactions; }

    public boolean isExpanded() { return expanded; }
    public void setExpanded(boolean expanded) { this.expanded = expanded; }
}
