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
    public Double hitungVolume() {
        return 2 * 3.14 * jariJari;
    }

    
}