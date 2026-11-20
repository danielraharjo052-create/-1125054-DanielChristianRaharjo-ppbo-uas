package nusazoo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nusazoo.model.Dog;
import nusazoo.model.Kandang;
import nusazoo.model.Printable;

/** Static utility class: not instantiable. */
public final class PrintUtil {
    private PrintUtil() {
    }

    public static <T> void printList(List<T> list) {
        int i = 1;
        for (T item : list) System.out.println((i++) + ". " + item);
    }

    public static <T> void printList(T[] array) {
        printList(Arrays.asList(array));
    }

    public static <T extends Printable> void printPrintableList(List<T> list) {
        int i = 1;
        for (T item : list) System.out.println((i++) + ". " + item.getPrintDetail());
    }

    /** Sorts a copy, so the original list keeps its order. */
    public static <T extends Comparable<? super T>> void printListSorted(List<T> list) {
        List<T> copy = new ArrayList<>(list);
        Collections.sort(copy);
        printList(copy);
    }

    public static <T extends Comparable<? super T>> void printListSorted(T[] array) {
        T[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);
        printList(copy);
    }

    /** Multiple bound: T must be Printable AND Comparable. */
    public static <T extends Printable & Comparable<? super T>> T findMaxPrintable(List<T> list) {
        if (list.isEmpty()) return null;
        T max = list.get(0);
        for (T item : list) if (item.compareTo(max) > 0) max = item;
        return max;
    }

    /** Upper bounded wildcard: read-only producer of Kandang. */
    public static double totalCapacity(List<? extends Kandang> list) {
        double total = 0;
        for (Kandang k : list) total += k.getKapasitas();
        return total;
    }

    /*
     * Lower bounded wildcard: List<Animal> and List<Object> can hold a Dog (consumer),
     * but List<Cat> cannot, because a Dog is not a Cat.
     */
    public static void addDefaultAnimals(List<? super Dog> target) {
        target.add(new Dog("Rex", 2, "Meat", 4));
        target.add(new Dog("Bella", 4, "Meat", 4));
    }
}
