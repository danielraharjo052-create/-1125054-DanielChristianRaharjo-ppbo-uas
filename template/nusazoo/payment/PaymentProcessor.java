package nusazoo.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import nusazoo.model.AuthorizationFailedException;
import nusazoo.model.InvalidInputException;
import nusazoo.model.PaymentFailedException;

public class PaymentProcessor {
    private final Prompter prompter;
    private final List<Consumer<String>> listeners = new ArrayList<>();

    public PaymentProcessor(Prompter prompter) {
        this.prompter = prompter;
    }

    /** Observers (e.g. the chat bot) are notified without the processor knowing them. */
    public void addListener(Consumer<String> listener) {
        listeners.add(listener);
    }

    public void processPayment(PaymentStrategy strategy, double amount) {
        if (amount <= 0) throw new InvalidInputException("Nominal harus lebih dari 0");
        if (!strategy.canPay(amount)) throw new PaymentFailedException("Nominal di luar batas metode ini");
        if (!strategy.validate(amount)) throw new PaymentFailedException("Saldo atau limit tidak mencukupi");
        for (AuthorizationMethod method : strategy.getRequiredAuthorizations()) {
            if (!method.getStrategy().authorize(strategy, prompter))
                throw new AuthorizationFailedException("Otorisasi " + method + " gagal");
        }
        strategy.pay(amount);
        String msg = String.format("Pembayaran berhasil: Rp %,.0f via %s", amount, strategy);
        for (Consumer<String> l : listeners) l.accept(msg);
    }
}
