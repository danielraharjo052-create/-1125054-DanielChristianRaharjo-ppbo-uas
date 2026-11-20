package util;
import java.util.Scanner;

public class CLIUtil {

    private static Scanner input = new Scanner(System.in);

    public static String inputString(String message) {
        System.out.print(message);
        return input.nextLine();
    }

    public static Double inputDouble(String message)
            throws InputException {

        System.out.print(message);

                