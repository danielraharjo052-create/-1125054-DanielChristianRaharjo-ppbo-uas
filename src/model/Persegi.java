package model;

public class Persegi extends SegiEmpat {

    private Double sisi;

    public Persegi(String label, Double sisi) {
        super(label, sisi, sisi);
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
            {"luas", hitungLuas().toString()},
            {"keliling", hitungKeliling().toString()},
            {"tipe", "Persegi"},
            {"sisi", sisi.toString()}
        };
    }
}