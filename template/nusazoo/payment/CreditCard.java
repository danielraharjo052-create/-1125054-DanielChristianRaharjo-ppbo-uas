package nusazoo.payment;

import java.util.List;

public class CreditCard implements PaymentStrategy, PinHolder, CcvHolder {
    private final String cardNumber;
    private final double limit;
    private double currentDebt;
    private final String ccv;
    private final String pin;

    public CreditCard(String cardNumber, double limit, String ccv, String pin) {
        this.cardNumber = cardNumber;
        this.limit = limit;
        this.ccv = ccv;
        this.pin = pin;
    }

    @Override public boolean validate(double amount) { return currentDebt + amount <= limit; }
    @Override public void pay(double amount) { currentDebt += amount; }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(AuthorizationMethod.CCV, AuthorizationMethod.PIN);
    }

    @Override public boolean canPay(double amount) { return amount >= 100_000 && amount <= 100_000_000; }
    @Override public boolean verifyPin(String input) { return pin.equals(input); }
    @Override public boolean verifyCcv(String input) { return ccv.equals(input); }

    @Override
    public String toString() {
        return String.format("Credit %s (limit tersisa Rp %,.0f)", cardNumber, limit - currentDebt);
    }
}
