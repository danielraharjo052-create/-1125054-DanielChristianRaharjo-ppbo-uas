package nusazoo.payment;

import java.util.List;

public class DebitCard implements PaymentStrategy, PinHolder {
    private final String cardNumber;
    private double balance;
    private final String pin;

    public DebitCard(String cardNumber, double balance, String pin) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.pin = pin;
    }

    @Override public boolean validate(double amount) { return balance >= amount; }
    @Override public void pay(double amount) { balance -= amount; }
    @Override public List<AuthorizationMethod> getRequiredAuthorizations() { return List.of(AuthorizationMethod.PIN); }
    @Override public boolean canPay(double amount) { return amount <= 10_000_000; }
    @Override public boolean verifyPin(String input) { return pin.equals(input); }

    @Override
    public String toString() {
        return String.format("Debit %s (saldo Rp %,.0f)", cardNumber, balance);
    }
}
