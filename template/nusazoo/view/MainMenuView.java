package nusazoo.view;

public class MainMenuView {
    private final ConsoleIO io;

    public MainMenuView(ConsoleIO io) {
        this.io = io;
    }

    public int mainMenu() {
        io.show("\n=== NUSAZOO MAIN MENU ===");
        io.show("1. Manajemen Hewan\n2. Manajemen Pengunjung\n3. Manajemen Kandang\n4. Manajemen Tiket");
        io.show("5. Layanan Pembayaran\n6. Ruang Obrolan\n7. Laporan & Pengurutan\n0. Keluar");
        return io.askInt("Pilih menu");
    }

    public int crudMenu(String title, boolean animalExtras) {
        io.show("\n=== PILIH AKSI: " + title + " ===");
        io.show("1. Tampilkan semua data\n2. Cari satu data\n3. Tambah data\n4. Ubah data\n5. Delete data\n6. Undo delete terakhir");
        if (animalExtras) io.show("7. Aktivitas satu hewan\n8. Beri makan semua hewan");
        io.show("0. Kembali");
        return io.askInt("Pilih aksi");
    }

    public int reportMenu() {
        io.show("\n=== LAPORAN & PENGURUTAN ===");
        io.show("1. Pengurutan hewan (nama, umur, habitat)\n2. Laporan generik (Report)\n3. Demo PrintUtil & BoxList\n0. Kembali");
        return io.askInt("Pilih");
    }

    public String ask(String q) { return io.ask(q); }

    public int askInt(String q) { return io.askInt(q); }

    public void show(String msg) { io.show(msg); }
}
