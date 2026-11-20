package model;

public class Kerucut extends Object3D {

    private Double jariJari;
    private Double tinggi;

    public Kerucut(String label, Double jariJari, Double tinggi) {
        super(label);
        this.jariJari = jariJari;
        this.tinggi = tinggi;
    }

    public Double jariJariGet() {
        return jariJari;
    }

    public void jariJariSet(Double jariJari) {
        this.jariJari = jariJari;
    }

    public Double tinggiGet() {
        return tinggi;
    }

    public void tinggiSet(Double tinggi) {
        this.tinggi = tinggi;
    }

    @Override
    public Double hitungLuasPermukaan() {
        return 3.14 * jariJari *( tinggi + jariJari );
    }

    @Override
    public Double hitungVolume() {
        return (3.14 * jariJari * jariJari * tinggi) / 3;
    }

    @Override
    public String[][] getKeyValueDetails() {
        String[][] parent = super.getKeyValueDetails();

        return new String[][] {
            parent[0],
            {"luas permukaan", hitungLuasPermukaan().toString()},
            {"volume", hitungVolume().toString()},
            {"tipe", "Kerucut"},
            {"jari-jari", jariJari.toString()},
            {"tinggi", tinggi.toString()}
        };
    }
}