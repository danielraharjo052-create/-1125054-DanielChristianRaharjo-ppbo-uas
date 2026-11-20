package model;

public class Kubus extends Balok {

    private Double sisi;

    public Kubus(String label, Double sisi) {
        super(label, sisi, sisi, sisi);
        this.sisi = sisi;
    }

    public Double sisiGet() {
        return sisi;
    }

    public void sisiSet(Double sisi) {
        this.sisi = sisi;
    }

    @Override
    public String[][] getKeyValueDetails() {
        String[][] parent = super.getKeyValueDetails();

        return new String[][] {
            parent[0],
            {"luas permukaan", hitungLuasPermukaan().toString()},
            {"volume", hitungVolume().toString()},
            {"tipe", "Kubus"},
            {"sisi", sisi.toString()}
        };
    }
}