package ru.job4j.atomicity.moneytransfer;

public class User {
    int id;
    int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }
}
