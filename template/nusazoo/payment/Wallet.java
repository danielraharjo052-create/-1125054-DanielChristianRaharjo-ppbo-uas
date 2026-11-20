package nusazoo.payment;

import java.util.List;

public class Wallet implements PaymentStrategy {
    private final String name;
    private double cashValue;

    public Wallet(String name, double cashValue) {
        this.name = name;
        this.cashValue = cashValue;
    }

    @Override public boolean validate(double amount) { return cashValue >= amount; }
    @Override public void pay(double amount) { cashValue -= amount; }
    @Override public List<AuthorizationMethod> getRequiredAuthorizations() { return List.of(); }
    @Override public boolean canPay(double amount) { return amount >= 100 && amount <= 1_000_000; }

    @Override
    public String toString() {
        return String.format("Cash '%s' (sisa Rp %,.0f)", name, cashValue);
    }
}
