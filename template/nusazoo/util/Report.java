package nusazoo.util;

import java.util.ArrayList;
import java.util.List;
import nusazoo.model.Printable;

public class Report<T extends Printable & Comparable<T>> {
    private final List<T> items = new ArrayList<>();

    /** Accepts List<Dog> for Report<Animal> thanks to the upper bounded wildcard. */
    public Report(List<? extends T> source) {
        items.addAll(source);
    }

    public List<T> sorted() {
        List<T> copy = new ArrayList<>(items);
        copy.sort(null);
        return copy;
    }

    public T max() {
        return items.isEmpty() ? null : sorted().get(items.size() - 1);
    }

    public T min() {
        return items.isEmpty() ? null : sorted().get(0);
    }

    public void print(String title) {
        System.out.println("=== " + title + " ===");
        PrintUtil.printPrintableList(sorted());
        if (!items.isEmpty()) {
            System.out.println("Terbesar : " + max().getPrintDetail());
            System.out.println("Terkecil : " + min().getPrintDetail());
        }
    }
}
