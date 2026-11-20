package nusazoo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import nusazoo.model.*;
import nusazoo.payment.*;
import nusazoo.util.Box;
import nusazoo.util.BoxList;
import nusazoo.util.ComparableBox;
import nusazoo.view.ConsoleIO;
import nusazoo.view.PaymentView;
import nusazoo.view.PengunjungDataView;
import nusazoo.view.TiketDataView;

public class PaymentController {
    private final PaymentView view;
    private final PaymentProcessor processor;
    private final PengunjungDataView visitors;
    private final TiketDataView tickets;
    private final Map<String, List<PaymentStrategy>> methods = new HashMap<>();
    private final BoxList<Box<Transaksi>> history = new BoxList<>();
    private int trxCounter = 0;

    public PaymentController(ConsoleIO io, PengunjungDataView visitors, TiketDataView tickets) {
        this.view = new PaymentView(io);
        this.processor = new PaymentProcessor(io);
        this.visitors = visitors;
        this.tickets = tickets;
    }

    public void addPaymentListener(Consumer<String> listener) {
        processor.addListener(listener);
    }

    public void addMethodFor(String visitorId, PaymentStrategy strategy) {
        methods.computeIfAbsent(visitorId.toLowerCase(), k -> new ArrayList<>()).add(strategy);
    }

    public void run() {
        int choice;
        do {
            choice = view.menu();
            try {
                switch (choice) {
                    case 0 -> { }
                    case 1 -> addMethod();
                    case 2 -> buyTicket();
                    case 3 -> {
                        if (history.isEmpty()) view.show("(belum ada transaksi)");
                        else history.printListSorted();
                    }
                    case 4 -> {
                        if (history.isEmpty()) view.show("(belum ada transaksi)");
                        else history.printList(); // original chronological order is preserved
                    }
                    default -> view.show("Pilihan tidak valid.");
                }
            } catch (RuntimeException e) {
                view.show("Gagal: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private String askVisitor() {
        String id = view.ask("ID pengunjung");
        if (visitors.findByPk(id) == null) {
            view.show("Pengunjung tidak ditemukan.");
            return null;
        }
        return id;
    }

    private double askDouble(String q) {
        try {
            return Double.parseDouble(view.ask(q));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Nominal harus berupa angka");
        }
    }

    private void addMethod() {
        String id = askVisitor();
        if (id == null) return;
        PaymentStrategy s;
        switch (view.methodTypeMenu()) {
            case 1 -> s = new Wallet(view.ask("Nama wallet"), askDouble("Saldo awal"));
            case 2 -> s = new DebitCard(view.ask("Nomor kartu"), askDouble("Saldo"), view.ask("PIN"));
            case 3 -> s = new CreditCard(view.ask("Nomor kartu"), askDouble("Limit"), view.ask("CCV"), view.ask("PIN"));
            case 4 -> s = new EWallet(view.ask("Nomor telepon"), askDouble("Saldo"), view.ask("PIN"));
            default -> {
                view.show("Jenis tidak valid.");
                return;
            }
        }
        addMethodFor(id, s);
        view.show("Metode ditambahkan: " + s);
    }

    private void buyTicket() {
        String id = askVisitor();
        if (id == null) return;
        int t = view.choose("Pilih jenis tiket:", List.of(TicketType.values()));
        if (t < 0) return;
        TicketType type = TicketType.values()[t];
        int qty = view.askInt("Jumlah tiket");
        if (qty <= 0) {
            view.show("Jumlah harus lebih dari 0.");
            return;
        }
        double amount = type.getBasePrice() * qty;
        List<PaymentStrategy> usable = methods.getOrDefault(id.toLowerCase(), List.of()).stream()
                .filter(m -> m.canPay(amount)).toList(); // filter by canPay before showing
        if (usable.isEmpty()) {
            view.show(String.format("Tidak ada metode yang bisa membayar Rp %,.0f. Tambahkan metode dulu.", amount));
            return;
        }
        int m = view.choose(String.format("Total Rp %,.0f. Pilih metode:", amount), usable);
        if (m < 0) return;
        PaymentStrategy chosen = usable.get(m);
        try {
            processor.processPayment(chosen, amount);
        } catch (PaymentFailedException | AuthorizationFailedException | InvalidInputException e) {
            view.show("Pembayaran gagal: " + e.getMessage());
            return;
        }
        for (int i = 0; i < qty; i++)
            tickets.addRecord(new Tiket(Tiket.IdGenerator.next(), id, type, type.getBasePrice()));
        history.add(new ComparableBox<>(new Transaksi("TRX-" + (++trxCounter), chosen.toString(), amount, type + " x" + qty)));
        view.show("Pembelian berhasil, " + qty + " tiket dibuat.");
    }
}
