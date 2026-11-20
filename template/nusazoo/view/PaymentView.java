package nusazoo.view;

import java.util.List;

public class PaymentView {
    private final ConsoleIO io;

    public PaymentView(ConsoleIO io) {
        this.io = io;
    }

    public int menu() {
        io.show("\n=== LAYANAN PEMBAYARAN ===");
        io.show("1. Tambah metode pembayaran\n2. Beli tiket / donasi adopsi\n3. Riwayat transaksi (urut nominal)\n4. Riwayat transaksi (kronologis)\n0. Kembali");
        return io.askInt("Pilih");
    }

    public int methodTypeMenu() {
        io.show("Jenis metode: 1. Cash  2. Debit Card  3. Credit Card  4. E-Wallet");
        return io.askInt("Pilih jenis");
    }

    /** Returns the 0-based index chosen, or -1 when cancelled. */
    public int choose(String title, List<?> options) {
        io.show(title);
        for (int i = 0; i < options.size(); i++) io.show((i + 1) + ". " + options.get(i));
        int c = io.askInt("Pilih nomor (0 = batal)");
        return (c >= 1 && c <= options.size()) ? c - 1 : -1;
    }

    public String ask(String q) { return io.ask(q); }

    public int askInt(String q) { return io.askInt(q); }

    public void show(String msg) { io.show(msg); }
}
