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

        try {
            Double value = input.nextDouble();
            input.nextLine();

            if (value <= 0) {
                throw new InputException(
                    "Nilai wajib > dari 0"
                );
            }
            return value;

        } catch (Exception e) {
            input.nextLine();
            throw new InputException("Input harus dalam bentuk angka ya");
        }
    }

    public static Integer inputInteger(String message) {
        System.out.print(message);

        try {
            Integer value = input.nextInt();
            input.nextLine();
            return value;

        } catch (Exception e) {
            input.nextLine();
            return -1;
        }
    }
}

