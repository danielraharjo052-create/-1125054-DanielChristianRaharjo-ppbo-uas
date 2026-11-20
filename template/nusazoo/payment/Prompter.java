package nusazoo.payment;

/** Lets the payment layer talk to the user without knowing about the console. */
public interface Prompter {
    String ask(String question);

    void show(String message);
}
