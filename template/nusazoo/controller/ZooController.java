package nusazoo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nusazoo.model.*;
import nusazoo.payment.CreditCard;
import nusazoo.payment.DebitCard;
import nusazoo.payment.Wallet;
import nusazoo.util.Box;
import nusazoo.util.BoxList;
import nusazoo.util.ComparableBox;
import nusazoo.util.PrintUtil;
import nusazoo.util.Report;
import nusazoo.view.*;

public class ZooController {
    private final ConsoleIO io = new ConsoleIO();
    private final MainMenuView menu = new MainMenuView(io);
    private final AnimalDataView animals = new AnimalDataView(io);
    private final PengunjungDataView visitors = new PengunjungDataView(io);
    private final KandangDataView cages = new KandangDataView(io);
    private final TiketDataView tickets = new TiketDataView(io);
    private final ZooKeeper keeper = new ZooKeeper();
    private final ChatController chat = new ChatController(io);
    private final PaymentController payment = new PaymentController(io, visitors, tickets);

    public ZooController() {
        loadDummyData();
        // Wiring: payment notifications reach the chat through the mediator, not directly.
        payment.addPaymentListener(chat::announce);
    }

    private void loadDummyData() {
        animals.addRecord(new Dog("Bruno", 3, "Meat", 4));
        animals.addRecord(new Cat("Luna", 2, "Fish", 4));
        animals.addRecord(new Mouse("Mickey", 1, "Cheese", 4));
        animals.addRecord(new Fish("Nemo", 1, "Plankton", 20.0));
        animals.addRecord(new Shark("Jaws", 7, "Fish", 200.0));
        animals.addRecord(new Whale("Willy", 12, "Krill", 1000.0));
        animals.addRecord(new Eagle("Sky", 4, "Meat", 2.2));
        animals.addRecord(new Parrot("Kiki", 5, "Seeds", 0.5));
        animals.addRecord(new Duck("Donald", 2, "Grain", 0.9));
        animals.addRecord(new RobotDinosaur("Rexy", 1, "None", 2));
        visitors.addRecord(new Pengunjung("V001", "Andi", "andi@mail.com", 20));
        visitors.addRecord(new Pengunjung("V002", "Budi", "budi@mail.com", 25));
        visitors.addRecord(new Pengunjung("V003", "Cici", "cici@mail.com", 12));
        cages.addRecord(new Kandang("K01", "Savana", 10, HabitatType.LAND));
        cages.addRecord(new Kandang("K02", "Akuarium", 8, HabitatType.SEA));
        cages.addRecord(new Kandang("K03", "Aviary", 15, HabitatType.AIR));
        tickets.addRecord(new Tiket(Tiket.IdGenerator.next(), "V001", TicketType.REGULER, TicketType.REGULER.getBasePrice()));
        tickets.addRecord(new Tiket(Tiket.IdGenerator.next(), "V002", TicketType.VIP, TicketType.VIP.getBasePrice()));
        payment.addMethodFor("V001", new Wallet("Dompet Andi", 500_000));
        payment.addMethodFor("V001", new DebitCard("1234-5678", 5_000_000, "1234"));
        payment.addMethodFor("V001", new CreditCard("4000-1111", 10_000_000, "123", "1234"));
    }

    public void run() {
        int choice;
        do {
            choice = menu.mainMenu();
            try {
                switch (choice) {
                    case 0 -> menu.show("Terima kasih!");
                    case 1 -> crud(animals, "Hewan", true);
                    case 2 -> crud(visitors, "Pengunjung", false);
                    case 3 -> crud(cages, "Kandang", false);
                    case 4 -> crud(tickets, "Tiket", false);
                    case 5 -> payment.run();
                    case 6 -> chat.run();
                    case 7 -> reports();
                    default -> menu.show("Pilihan tidak valid.");
                }
            } catch (RuntimeException e) {
                menu.show("Terjadi kesalahan: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private void crud(DataView<?> view, String title, boolean animalExtras) {
        int c;
        do {
            c = menu.crudMenu(title, animalExtras);
            switch (c) {
                case 0 -> { }
                case 1 -> view.printAll();
                case 2 -> view.printByPk(menu.ask("Primary key"));
                case 3 -> view.showInsertForm();
                case 4 -> view.showUpdateForm();
                case 5 -> view.promptDelete();
                case 6 -> view.undoDelete();
                case 7 -> { if (animalExtras) animalActivity(); else menu.show("Pilihan tidak valid."); }
                case 8 -> { if (animalExtras) feedAll(); else menu.show("Pilihan tidak valid."); }
                default -> menu.show("Pilihan tidak valid.");
            }
        } while (c != 0);
    }

    private void animalActivity() {
        Animal a = animals.findByPk(menu.ask("Nama hewan"));
        if (a == null) {
            menu.show("Hewan tidak ditemukan.");
            return;
        }
        menu.show(a.printData());
        menu.show("Sound       : " + a.printSound());
        String move = keeper.describeMove(a);
        menu.show("Movement    : " + (move == null ? a.getName() + " tidak dapat bergerak (bukan Movable)." : move));
        int portions = menu.askInt("Jumlah porsi makan");
        boolean fed = portions == 1 ? keeper.feed(a) : keeper.feed(a, portions); // overloaded feed
        if (!fed) menu.show(a.getName() + " tidak dapat diberi makan (bukan Feedable).");
    }

    private void feedAll() {
        for (Animal a : animals.getRecords()) {
            if (!keeper.feed(a)) menu.show("- " + a.getName() + " dilewati (bukan Feedable).");
        }
    }

    private void reports() {
        int c;
        do {
            c = menu.reportMenu();
            switch (c) {
                case 0 -> { }
                case 1 -> sortAnimals();
                case 2 -> genericReports();
                case 3 -> genericDemo();
                default -> menu.show("Pilihan tidak valid.");
            }
        } while (c != 0);
    }

    private void sortAnimals() {
        List<Animal> byName = new ArrayList<>(animals.getRecords());
        Collections.sort(byName, new Comparator<Animal>() {
            @Override
            public int compare(Animal a1, Animal a2) {
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        });
        menu.show("=== HEWAN SORTED BY NAME (A - Z) ===");
        PrintUtil.printList(byName);

        List<Animal> byAge = new ArrayList<>(animals.getRecords());
        Collections.sort(byAge, new Comparator<Animal>() {
            @Override
            public int compare(Animal a1, Animal a2) {
                return Integer.compare(a1.getAge(), a2.getAge());
            }
        });
        menu.show("=== HEWAN SORTED BY AGE (ASCENDING) ===");
        for (int i = 0; i < byAge.size(); i++)
            menu.show((i + 1) + ". " + byAge.get(i).getName() + " - " + byAge.get(i).getAge() + " tahun");

        List<Animal> byHabitat = new ArrayList<>(animals.getRecords());
        Collections.sort(byHabitat, new Comparator<Animal>() {
            @Override
            public int compare(Animal a1, Animal a2) {
                int c = Integer.compare(rank(a1), rank(a2));
                return c != 0 ? c : a1.getName().compareToIgnoreCase(a2.getName());
            }
        });
        menu.show("=== HEWAN SORTED BY HABITAT (Land, Sea, Air) LALU NAMA ===");
        PrintUtil.printList(byHabitat);
    }

    private static int rank(Animal a) {
        if (a instanceof LandAnimal) return 0;
        if (a instanceof SeaAnimal) return 1;
        return 2;
    }

    private void genericReports() {
        new Report<Animal>(animals.getRecords()).print("Laporan Hewan (urut nama)");
        List<Dog> dogsOnly = new ArrayList<>();
        for (Animal a : animals.getRecords()) if (a instanceof Dog d) dogsOnly.add(d);
        new Report<Animal>(dogsOnly).print("Report<Animal> dari List<Dog>");
        new Report<Tiket>(tickets.getRecords()).print("Laporan Tiket (urut harga)");
    }

    private void genericDemo() {
        List<Animal> list = new ArrayList<>(animals.getRecords());
        Animal[] array = list.toArray(new Animal[0]);
        menu.show("--- printList (List) ---");
        PrintUtil.printList(list);
        menu.show("--- printList (array) ---");
        PrintUtil.printList(array);
        menu.show("--- printPrintableList ---");
        PrintUtil.printPrintableList(list);
        menu.show("--- printListSorted (List & array, data asli tidak berubah) ---");
        PrintUtil.printListSorted(list);
        PrintUtil.printListSorted(array);
        menu.show("Elemen pertama setelah sorting tetap: " + list.get(0));
        menu.show("Terbesar (multiple bound): " + PrintUtil.findMaxPrintable(list));
        menu.show("Total kapasitas kandang: " + PrintUtil.totalCapacity(cages.getRecords()));

        List<Animal> animalTarget = new ArrayList<>();
        List<Object> objectTarget = new ArrayList<>();
        PrintUtil.addDefaultAnimals(animalTarget);
        PrintUtil.addDefaultAnimals(objectTarget);
        menu.show("Lower bound wildcard: " + animalTarget.size() + " + " + objectTarget.size() + " Dog ditambahkan.");

        BoxList<Object> boxes = new BoxList<>();
        boxes.add(42);
        boxes.add("teks");
        boxes.add(list.get(0));
        boxes.add(new Box<>(3.14));
        boxes.add(new ComparableBox<>(7));
        menu.show("--- BoxList printList ---");
        boxes.printList();
        menu.show("--- BoxList printListSorted ---");
        boxes.printListSorted();
        try {
            boxes.get(99);
        } catch (IndexOutOfBoundsException e) {
            menu.show("Exception diharapkan: " + e.getMessage());
        }
    }
}
