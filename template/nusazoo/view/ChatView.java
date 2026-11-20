package nusazoo.view;

public class ChatView {
    private final ConsoleIO io;

    public ChatView(ConsoleIO io) {
        this.io = io;
    }

    public int menu(String currentUser) {
        io.show("\n=== RUANG OBROLAN (user aktif: " + (currentUser == null ? "-" : currentUser) + ") ===");
        io.show("1. Login sebagai user\n2. Join ruang\n3. Kirim broadcast\n4. Kirim pesan privat\n5. Kick (admin)");
        io.show("6. Mute (admin)\n7. Unmute (admin)\n8. Lihat riwayat\n9. Tambah kata kasar\n0. Kembali");
        return io.askInt("Pilih");
    }

    public String ask(String q) { return io.ask(q); }

    public void show(String msg) { io.show(msg); }
}
