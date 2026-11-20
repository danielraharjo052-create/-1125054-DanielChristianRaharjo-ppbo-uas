package model;

import java.util.List;

public class PrintUtil {

    public static void printGObjectDetail(
        List<? extends GObject> objects
    ) {
        if (objects.isEmpty()) {
            System.out.println("Belum ada objek.");
            return;
        }

        for (GObject item : objects) {

            System.out.println("\n--------------");

            String[][] details = item.getKeyValueDetails();

            for (String[] detail : details) {
                System.out.println(
                        detail[0] + " : " + detail[1]);
            }
            
            
        }

        System.out.println("--------------");
    }
}