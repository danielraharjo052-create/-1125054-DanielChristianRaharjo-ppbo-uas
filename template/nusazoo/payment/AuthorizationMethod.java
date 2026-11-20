package nusazoo.payment;

/** Each constant is mapped to its own strategy, so no switch/if is needed in the processor. */
public enum AuthorizationMethod {
    CCV(new CcvAuthorization()),
    PIN(new PinAuthorization()),
    OTP(new OtpAuthorization());

    private final AuthorizationStrategy strategy;

    AuthorizationMethod(AuthorizationStrategy strategy) {
        this.strategy = strategy;
    }

    public AuthorizationStrategy getStrategy() {
        return strategy;
    }
}
