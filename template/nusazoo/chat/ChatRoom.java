package nusazoo.chat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/** Mediator: all routing, moderation, admin rules, mute and history live here. */
public class ChatRoom {
    private final String roomName;
    private final List<User> members = new ArrayList<>();
    private final List<String> badWords = new ArrayList<>();
    private final Set<String> muted = new HashSet<>();
    private final List<String> history = new ArrayList<>();
    private final Consumer<String> out;
    private User admin;

    public ChatRoom(String roomName, Consumer<String> out) {
        this.roomName = roomName;
        this.out = out;
    }

    public String getRoomName() { return roomName; }
    public User getAdmin() { return admin; }
    public List<User> getMembers() { return List.copyOf(members); }

    public void registerUser(User user) {
        if (members.contains(user)) {
            out.accept("[ChatRoom - " + roomName + "] " + user.getName() + " sudah ada di ruang ini.");
            return;
        }
        members.add(user);
        user.attach(this);
        if (admin == null) admin = user; // first user to join is the admin
        out.accept("[ChatRoom - " + roomName + "] " + user.getName() + " bergabung"
                + (admin == user ? " (admin)." : "."));
    }

    /** Internal removal. If the admin leaves, the longest-standing member becomes admin. */
    public void removeUser(User user) {
        if (!members.remove(user)) return;
        user.detach(this);
        muted.remove(user.getName().toLowerCase());
        if (user == admin) {
            admin = members.isEmpty() ? null : members.get(0);
            if (admin != null) out.accept("[ChatRoom - " + roomName + "] Admin baru: " + admin.getName());
        }
    }

    public void kick(User requester, String targetName) {
        if (requester != admin) {
            out.accept("[ChatRoom - " + roomName + "] Hanya admin yang boleh kick.");
            return;
        }
        User target = find(targetName);
        if (target == null) {
            out.accept("[ChatRoom - " + roomName + "] User tidak ditemukan: " + targetName);
        } else if (target == admin) {
            out.accept("[ChatRoom - " + roomName + "] Admin tidak bisa meng-kick dirinya sendiri.");
        } else {
            removeUser(target);
            out.accept("[ChatRoom - " + roomName + "] " + target.getName() + " dikeluarkan oleh " + requester.getName());
        }
    }

    public void mute(User requester, String targetName) {
        changeMute(requester, targetName, true);
    }

    public void unmute(User requester, String targetName) {
        changeMute(requester, targetName, false);
    }

    private void changeMute(User requester, String targetName, boolean mute) {
        if (requester != admin) {
            out.accept("[ChatRoom - " + roomName + "] Hanya admin yang boleh mute/unmute.");
            return;
        }
        User target = find(targetName);
        if (target == null) {
            out.accept("[ChatRoom - " + roomName + "] User tidak ditemukan: " + targetName);
            return;
        }
        if (mute) muted.add(target.getName().toLowerCase());
        else muted.remove(target.getName().toLowerCase());
        out.accept("[ChatRoom - " + roomName + "] " + target.getName() + (mute ? " di-mute." : " di-unmute."));
    }

    public void addBadWord(String word) {
        if (word != null && !word.isBlank()) badWords.add(word.trim().toLowerCase());
    }

    public String filterMessage(String message) {
        String result = message;
        for (String bad : badWords) {
            result = result.replaceAll("(?i)" + java.util.regex.Pattern.quote(bad), "***");
        }
        return result;
    }

    public void sendBroadcast(User sender, String message) {
        sendBroadcast(sender, message, false);
    }

    /** Overload: urgent messages get a prefix. */
    public void sendBroadcast(User sender, String message, boolean urgent) {
        if (!members.contains(sender)) {
            out.accept("[ChatRoom - " + roomName + "] " + sender.getName() + " bukan anggota ruang ini.");
            return;
        }
        String clean = (urgent ? "[URGENT] " : "") + filterMessage(message);
        if (isMuted(sender)) {
            log(sender.getName(), "BROADCAST", clean + " [MUTED - not delivered]");
            out.accept("[ChatRoom - " + roomName + "] " + sender.getName() + " sedang di-mute, pesan tidak disebarkan.");
            return;
        }
        log(sender.getName(), "BROADCAST", clean);
        out.accept("[ChatRoom - " + roomName + "] (Broadcast dari " + sender.getName() + "): " + clean);
        for (User m : members) if (m != sender) m.receiveMessage(sender, clean);
    }

    public void sendPrivateMessage(User sender, String recipientName, String message) {
        if (!members.contains(sender)) {
            out.accept("[ChatRoom - " + roomName + "] " + sender.getName() + " bukan anggota ruang ini.");
            return;
        }
        User recipient = find(recipientName);
        if (recipient == null) {
            out.accept("[ChatRoom - " + roomName + "] Penerima tidak ditemukan: " + recipientName);
            return;
        }
        String clean = filterMessage(message);
        if (isMuted(sender)) {
            log(sender.getName(), "PRIVATE->" + recipient.getName(), clean + " [MUTED - not delivered]");
            out.accept("[ChatRoom - " + roomName + "] " + sender.getName() + " sedang di-mute, pesan tidak disebarkan.");
            return;
        }
        log(sender.getName(), "PRIVATE->" + recipient.getName(), clean);
        out.accept("[ChatRoom - " + roomName + "] (Private " + sender.getName() + " -> " + recipient.getName() + "): " + clean);
        recipient.receivePrivate(sender, clean);
    }

    public void showHistory() {
        out.accept("=== RIWAYAT " + roomName + " ===");
        if (history.isEmpty()) out.accept("(belum ada pesan)");
        for (String h : history) out.accept(h);
    }

    private boolean isMuted(User u) {
        return muted.contains(u.getName().toLowerCase());
    }

    private User find(String name) {
        for (User m : members) if (m.getName().equalsIgnoreCase(name)) return m;
        return null;
    }

    private void log(String sender, String type, String text) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        history.add("[" + time + "] " + sender + " (" + type + "): " + text);
    }
}
