package nusazoo.payment;

import java.util.List;

/** Added later (Open/Closed): only a new class, processor and interface untouched. */
public class EWallet implements PaymentStrategy, PinHolder {
    private final String phoneNumber;
    private double balance;
    private final String pin;

    public EWallet(String phoneNumber, double balance, String pin) {
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.pin = pin;
    }

    @Override public boolean validate(double amount) { return balance >= amount; }
    @Override public void pay(double amount) { balance -= amount; }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(AuthorizationMethod.PIN, AuthorizationMethod.OTP);
    }

    // Assumption: e-wallet transactions are limited to Rp 100 .. Rp 5.000.000
    @Override public boolean canPay(double amount) { return amount >= 100 && amount <= 5_000_000; }
    @Override public boolean verifyPin(String input) { return pin.equals(input); }

    @Override
    public String toString() {
        return String.format("E-Wallet %s (saldo Rp %,.0f)", phoneNumber, balance);
    }
}
