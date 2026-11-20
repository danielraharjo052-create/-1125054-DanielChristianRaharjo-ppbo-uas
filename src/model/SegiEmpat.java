package model;

public class SegiEmpat extends Object2D {

    private Double panjang;
    private Double lebar;

    public SegiEmpat(String label, Double panjang, Double lebar) {
        super(label);
        this.panjang = panjang;
        this.lebar = lebar;
    }

    public Double panjangGet() {
        return panjang;
    }

    public void panjangSet(Double panjang) {
        this.panjang = panjang;
    }

    public Double lebarGet() {
        return lebar;
    }

    public void lebarSet(Double lebar) {
        this.lebar = lebar;
    }

    @Override
    public Double hitungLuas() {
        return panjang * lebar;
    }

    @Override
    public Double hitungKeliling() {
        return 2 * (panjang + lebar);
    }

    @Override
    public String[][] getKeyValueDetails() {
        String[][] parent = super.getKeyValueDetails();

        return new String[][] {
            parent[0],
            {"luas", hitungLuas().toString()},
            {"keliling", hitungKeliling().toString()},
            {"tipe", "Segi Empat"},
            {"panjang", panjang.toString()},
            {"lebar", lebar.toString()}
        };
    }
}