package nusazoo.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** Colleague: knows only the rooms it joined, never other users. */
public class User {
    private final String name;
    private final List<ChatRoom> rooms = new ArrayList<>();
    private final Consumer<String> output;

    public User(String name, Consumer<String> output) {
        this.name = name;
        this.output = output;
    }

    public String getName() { return name; }

    void attach(ChatRoom room) {
        if (!rooms.contains(room)) rooms.add(room);
    }

    void detach(ChatRoom room) {
        rooms.remove(room);
    }

    public boolean isIn(ChatRoom room) { return rooms.contains(room); }

    public void send(ChatRoom room, String message) {
        output.accept("[" + name + "]: Memproses pengiriman pesan grup...");
        room.sendBroadcast(this, message);
    }

    public void send(String message) {
        if (rooms.isEmpty()) {
            output.accept("[" + name + "]: Belum bergabung ke ruang mana pun.");
            return;
        }
        send(rooms.get(0), message);
    }

    public void sendPrivate(ChatRoom room, String recipientName, String message) {
        output.accept("[" + name + "]: Mengirim pesan privat ke " + recipientName + "...");
        room.sendPrivateMessage(this, recipientName, message);
    }

    public void receiveMessage(User sender, String message) {
        output.accept("[" + name + "] Menerima pesan dari " + sender.name + ": " + message);
    }

    public void receivePrivate(User sender, String message) {
        output.accept("[" + name + "] Menerima pesan privat dari " + sender.name + ": " + message);
    }
}
