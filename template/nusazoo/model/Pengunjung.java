package nusazoo.model;

public class Pengunjung implements Printable {
    private String idPengunjung;
    private String nama;
    private String email;
    private int umur;

    public Pengunjung(String idPengunjung, String nama, String email, int umur) {
        setIdPengunjung(idPengunjung);
        setNama(nama);
        setEmail(email);
        setUmur(umur);
    }

    public String getIdPengunjung() { return idPengunjung; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public int getUmur() { return umur; }

    public final void setIdPengunjung(String id) {
        if (id == null || id.isBlank()) throw new InvalidInputException("ID pengunjung tidak boleh kosong");
        this.idPengunjung = id.trim();
    }

    public final void setNama(String nama) {
        if (nama == null || nama.isBlank()) throw new InvalidInputException("Nama tidak boleh kosong");
        this.nama = nama.trim();
    }

    public final void setEmail(String email) {
        if (email == null || !email.contains("@")) throw new InvalidInputException("Email tidak valid");
        this.email = email.trim();
    }

    public final void setUmur(int umur) {
        if (umur < 0) throw new InvalidInputException("Umur tidak boleh negatif");
        this.umur = umur;
    }

    @Override
    public String getPrintDetail() {
        return idPengunjung + " - " + nama + " (" + umur + " tahun, " + email + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Pengunjung p && p.idPengunjung.equalsIgnoreCase(idPengunjung);
    }

    @Override
    public int hashCode() {
        return idPengunjung.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return getPrintDetail();
    }
}
