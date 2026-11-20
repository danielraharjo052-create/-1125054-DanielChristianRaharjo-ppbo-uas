package nusazoo.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaksi implements Printable, Comparable<Transaksi> {
    private final String id;
    private final String method;
    private final double amount;
    private final String purpose;
    private final String time;

    public Transaksi(String id, String method, double amount, String purpose) {
        this.id = id;
        this.method = method;
        this.amount = amount;
        this.purpose = purpose;
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public double getAmount() { return amount; }

    @Override
    public String getPrintDetail() {
        return String.format("%s [%s] %s via %s: Rp %,.0f", id, time, purpose, method, amount);
    }

    @Override
    public int compareTo(Transaksi o) {
        int c = Double.compare(amount, o.amount);
        return c != 0 ? c : id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return getPrintDetail();
    }
}
