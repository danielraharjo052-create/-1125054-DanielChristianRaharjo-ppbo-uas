package nusazoo.payment;

import java.security.SecureRandom;

public class OtpAuthorization implements AuthorizationStrategy {
    private final SecureRandom random = new SecureRandom();

    @Override
    public boolean authorize(PaymentStrategy payer, Prompter prompter) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        prompter.show("[SMS] Kode OTP Anda: " + code);
        return code.equals(prompter.ask("Masukkan OTP"));
    }
}
