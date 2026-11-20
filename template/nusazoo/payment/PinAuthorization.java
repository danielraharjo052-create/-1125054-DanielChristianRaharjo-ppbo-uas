package nusazoo.payment;

public class PinAuthorization implements AuthorizationStrategy {
    @Override
    public boolean authorize(PaymentStrategy payer, Prompter prompter) {
        if (!(payer instanceof PinHolder holder)) return false;
        return holder.verifyPin(prompter.ask("Masukkan PIN"));
    }
}
