package nusazoo.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import nusazoo.chat.ChatRoom;
import nusazoo.chat.User;
import nusazoo.view.ChatView;
import nusazoo.view.ConsoleIO;

public class ChatController {
    private final ChatView view;
    private final Map<String, ChatRoom> rooms = new LinkedHashMap<>();
    private final Map<String, User> users = new LinkedHashMap<>();
    private final User systemBot;
    private User current;
    private boolean quiet = true; // silence the seeding phase

    public ChatController(ConsoleIO io) {
        this.view = new ChatView(io);
        Consumer<String> out = msg -> {
            if (!quiet) view.show(msg);
        };
        for (String r : new String[]{"General", "Keeper", "Veterinary"}) {
            ChatRoom room = new ChatRoom(r, out);
            room.addBadWord("bodoh");
            room.addBadWord("goblok");
            rooms.put(r.toLowerCase(), room);
        }
        for (String n : new String[]{"Budi", "Andi", "Cici", "Dewi"}) users.put(n.toLowerCase(), new User(n, out));
        systemBot = new User("SystemBot", out);
        // The first user who joins becomes the admin of that room.
        rooms.get("general").registerUser(users.get("budi"));
        rooms.get("general").registerUser(users.get("andi"));
        rooms.get("general").registerUser(users.get("cici"));
        rooms.get("general").registerUser(systemBot);
        rooms.get("keeper").registerUser(users.get("andi"));
        rooms.get("keeper").registerUser(users.get("budi"));
        rooms.get("veterinary").registerUser(users.get("dewi"));
        rooms.get("veterinary").registerUser(users.get("cici"));
        quiet = false;
    }

    /** Called by the wiring in ZooController when a payment succeeds. */
    public void announce(String message) {
        systemBot.send(rooms.get("general"), "[SYSTEM] " + message);
    }

    public void run() {
        int choice;
        do {
            choice = view.menu(current == null ? null : current.getName());
            try {
                handle(choice);
            } catch (RuntimeException e) {
                view.show("Terjadi kesalahan: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private void handle(int choice) {
        switch (choice) {
            case 0 -> { }
            case 1 -> {
                String n = view.ask("Nama user (baru akan dibuat jika belum ada)");
                if (n.isBlank()) {
                    view.show("Nama tidak boleh kosong.");
                } else {
                    current = users.computeIfAbsent(n.toLowerCase(), k -> new User(n, view::show));
                    view.show("Login sebagai " + current.getName());
                }
            }
            case 2, 3, 4, 5, 6, 7, 8 -> roomAction(choice);
            case 9 -> {
                ChatRoom r = pickRoom();
                if (r != null) {
                    r.addBadWord(view.ask("Kata kasar"));
                    view.show("Kata ditambahkan.");
                }
            }
            default -> view.show("Pilihan tidak valid.");
        }
    }

    private void roomAction(int choice) {
        if (current == null && choice != 8) {
            view.show("Login dulu (menu 1).");
            return;
        }
        ChatRoom room = pickRoom();
        if (room == null) return;
        switch (choice) {
            case 2 -> room.registerUser(current);
            case 3 -> current.send(room, view.ask("Pesan"));
            case 4 -> {
                String to = view.ask("Nama penerima");
                current.sendPrivate(room, to, view.ask("Pesan"));
            }
            case 5 -> room.kick(current, view.ask("Nama user yang di-kick"));
            case 6 -> room.mute(current, view.ask("Nama user yang di-mute"));
            case 7 -> room.unmute(current, view.ask("Nama user yang di-unmute"));
            case 8 -> room.showHistory();
            default -> { }
        }
    }

    private ChatRoom pickRoom() {
        view.show("Ruang tersedia: " + String.join(", ", rooms.values().stream().map(ChatRoom::getRoomName).toList()));
        ChatRoom r = rooms.get(view.ask("Nama ruang").toLowerCase());
        if (r == null) view.show("Ruang tidak ditemukan.");
        return r;
    }
}
