package nusazoo.model;

public class Kandang implements Printable {
    private String kodeKandang;
    private String nama;
    private int kapasitas;
    private HabitatType habitat;

    public Kandang(String kodeKandang, String nama, int kapasitas, HabitatType habitat) {
        setKodeKandang(kodeKandang);
        setNama(nama);
        setKapasitas(kapasitas);
        setHabitat(habitat);
    }

    public String getKodeKandang() { return kodeKandang; }
    public String getNama() { return nama; }
    public int getKapasitas() { return kapasitas; }
    public HabitatType getHabitat() { return habitat; }

    public final void setKodeKandang(String k) {
        if (k == null || k.isBlank()) throw new InvalidInputException("Kode kandang tidak boleh kosong");
        this.kodeKandang = k.trim();
    }

    public final void setNama(String n) {
        if (n == null || n.isBlank()) throw new InvalidInputException("Nama kandang tidak boleh kosong");
        this.nama = n.trim();
    }

    public final void setKapasitas(int k) {
        if (k < 0) throw new InvalidInputException("Kapasitas tidak boleh negatif");
        this.kapasitas = k;
    }

    public final void setHabitat(HabitatType h) {
        if (h == null) throw new InvalidInputException("Habitat tidak boleh kosong");
        this.habitat = h;
    }

    @Override
    public String getPrintDetail() {
        return kodeKandang + " - " + nama + " [" + habitat + ", kapasitas " + kapasitas + "]";
    }

    @Override
    public String toString() {
        return getPrintDetail();
    }
}
