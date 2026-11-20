package nusazoo.payment;

public interface AuthorizationStrategy {
    boolean authorize(PaymentStrategy payer, Prompter prompter);
}
