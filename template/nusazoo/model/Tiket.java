package nusazoo.model;

public class Tiket implements Printable, Comparable<Tiket> {
    private String kodeTiket;
    private String idPengunjung;
    private TicketType jenis;
    private double harga;

    public Tiket(String kodeTiket, String idPengunjung, TicketType jenis, double harga) {
        if (kodeTiket == null || kodeTiket.isBlank()) throw new InvalidInputException("Kode tiket tidak boleh kosong");
        if (idPengunjung == null || idPengunjung.isBlank()) throw new InvalidInputException("ID pengunjung tidak boleh kosong");
        if (jenis == null) throw new InvalidInputException("Jenis tiket tidak boleh kosong");
        if (harga < 0) throw new InvalidInputException("Harga tidak boleh negatif");
        this.kodeTiket = kodeTiket.trim();
        this.idPengunjung = idPengunjung.trim();
        this.jenis = jenis;
        this.harga = harga;
    }

    public String getKodeTiket() { return kodeTiket; }
    public String getIdPengunjung() { return idPengunjung; }
    public TicketType getJenis() { return jenis; }
    public double getHarga() { return harga; }

    @Override
    public String getPrintDetail() {
        return kodeTiket + " - " + jenis + " untuk " + idPengunjung + String.format(" (Rp %,.0f)", harga);
    }

    /** Price first, then code. */
    @Override
    public int compareTo(Tiket o) {
        int c = Double.compare(harga, o.harga);
        return c != 0 ? c : kodeTiket.compareTo(o.kodeTiket);
    }

    @Override
    public String toString() {
        return getPrintDetail();
    }

    /** Generates codes such as TKT-0001. */
    public static class IdGenerator {
        private static int counter = 0;

        private IdGenerator() {
        }

        public static String next() {
            return String.format("TKT-%04d", ++counter);
        }
    }
}
