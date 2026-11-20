package nusazoo.view;

import java.util.Scanner;
import nusazoo.payment.Prompter;

/** The only class that touches System.in. */
public class ConsoleIO implements Prompter {
    private final Scanner in = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.print(question + ": ");
        return in.hasNextLine() ? in.nextLine().trim() : "";
    }

    @Override
    public void show(String message) {
        System.out.println(message);
    }

    /** Returns 0 on end of input so loops can always finish. */
    public int askInt(String question) {
        while (true) {
            if (!in.hasNextLine()) return 0;
            System.out.print(question + ": ");
            String line = in.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }
}
