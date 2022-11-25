package fr.soraxdubbing.itemmodifiers;

public class Attribute {
    private String name;
    private double Amount;

    public Attribute(String name, double amount) {
        this.name = name;
        Amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }
}
