package nusazoo.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Template Method: the CRUD skeleton is fixed here, the four abstract methods are the hooks.
 */
public abstract class DataView<T> {
    private final List<T> records = new ArrayList<>();
    private final ConsoleIO io;
    private T lastDeleted;
    private int lastDeletedIndex = -1;

    protected DataView(ConsoleIO io) {
        this.io = io;
    }

    protected abstract String getPrimaryKeyValue(T data);

    protected abstract String[] getFields();

    protected abstract T convertValuesToModel(String[] values);

    protected abstract String[] convertModelToValues(T data);

    /** Used by controllers to seed or add data programmatically. */
    public void addRecord(T data) {
        records.add(data);
    }

    public List<T> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public T findByPk(String pk) {
        int i = indexOf(pk);
        return i < 0 ? null : records.get(i);
    }

    /** Returns a sorted copy; the stored records keep their order. */
    public List<T> getAllSorted(Comparator<? super T> comparator) {
        List<T> copy = new ArrayList<>(records);
        copy.sort(comparator);
        return copy;
    }

    public void printAll() {
        if (records.isEmpty()) {
            io.show("(data kosong)");
            return;
        }
        printHeader();
        for (T r : records) printRow(convertModelToValues(r));
    }

    public void printByPk(String primaryKey) {
        int i = indexOf(primaryKey);
        if (i < 0) {
            io.show("Data dengan kunci '" + primaryKey + "' tidak ditemukan.");
            return;
        }
        printHeader();
        printRow(convertModelToValues(records.get(i)));
    }

    public void showInsertForm() {
        try {
            T model = convertValuesToModel(promptValues());
            if (indexOf(getPrimaryKeyValue(model)) >= 0) {
                io.show("Gagal: primary key sudah ada.");
                return;
            }
            records.add(model);
            io.show("Data berhasil ditambahkan.");
        } catch (RuntimeException e) {
            io.show("Input tidak valid: " + e.getMessage());
        }
    }

    public void showUpdateForm() {
        String pk = io.ask("Primary key data yang diubah");
        int index = indexOf(pk);
        if (index < 0) {
            io.show("Data tidak ditemukan.");
            return;
        }
        try {
            T model = convertValuesToModel(promptValues());
            int clash = indexOf(getPrimaryKeyValue(model));
            if (clash >= 0 && clash != index) {
                io.show("Gagal: primary key baru sudah dipakai data lain.");
                return;
            }
            records.set(index, model);
            io.show("Data berhasil diubah.");
        } catch (RuntimeException e) {
            io.show("Input tidak valid: " + e.getMessage());
        }
    }

    public void promptDelete() {
        String pk = io.ask("Primary key data yang dihapus");
        int index = indexOf(pk);
        if (index < 0) {
            io.show("Data tidak ditemukan.");
            return;
        }
        lastDeleted = records.remove(index);
        lastDeletedIndex = index;
        io.show("Data berhasil dihapus.");
    }

    /** Bonus: restores the most recently deleted record. */
    public void undoDelete() {
        if (lastDeleted == null) {
            io.show("Tidak ada penghapusan yang bisa dibatalkan.");
            return;
        }
        if (indexOf(getPrimaryKeyValue(lastDeleted)) >= 0) {
            io.show("Gagal: primary key sudah dipakai lagi.");
            return;
        }
        records.add(Math.min(lastDeletedIndex, records.size()), lastDeleted);
        lastDeleted = null;
        io.show("Penghapusan terakhir dibatalkan.");
    }

    private String[] promptValues() {
        String[] fields = getFields();
        String[] values = new String[fields.length];
        for (int i = 0; i < fields.length; i++) values[i] = io.ask(fields[i]);
        return values;
    }

    private int indexOf(String pk) {
        for (int i = 0; i < records.size(); i++)
            if (getPrimaryKeyValue(records.get(i)).equalsIgnoreCase(pk.trim())) return i;
        return -1;
    }

    private void printHeader() {
        printRow(getFields());
        io.show("-".repeat(getFields().length * 20));
    }

    private void printRow(String[] cells) {
        StringBuilder sb = new StringBuilder();
        for (String c : cells) sb.append(String.format("%-20s", c));
        io.show(sb.toString());
    }
}
