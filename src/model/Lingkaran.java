package model;

public class Lingkaran extends Object2D {

    private Double jariJari;

    public Lingkaran(String label, Double jariJari) {
        super(label);
        this.jariJari = jariJari;
    }

    public Double jariJariGet() {
        return jariJari;
    }

    public void jariJariSet(Double jariJari) {
        this.jariJari = jariJari;
    }

    @Override
    public Double hitungLuas() {
        return 3.14 * jariJari * jariJari;
    }

    @Override
    public Double hitungKeliling() {
        return 2 * 3.14 * jariJari;
    }

    @Override
    public String[][] getKeyValueDetails() {
        String[][] parent = super.getKeyValueDetails();

        return new String[][] {
            parent[0],
            {"luas", hitungLuas().toString()},
            {"keliling", hitungKeliling().toString()},
            {"tipe", "Lingkaran"},
            {"jari-jari", jariJari.toString()}
        };
    }
}