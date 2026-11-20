package nusazoo.payment;

public class CcvAuthorization implements AuthorizationStrategy {
    @Override
    public boolean authorize(PaymentStrategy payer, Prompter prompter) {
        if (!(payer instanceof CcvHolder holder)) return false;
        return holder.verifyCcv(prompter.ask("Masukkan CCV"));
    }
}
