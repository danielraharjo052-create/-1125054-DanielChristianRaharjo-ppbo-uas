package model;

public class Balok extends Object3D {

    private Double panjang;
    private Double lebar;
    private Double tinggi;

    public Balok(String label, Double panjang, Double lebar, Double tinggi) {
        super(label);
        this.panjang = panjang;
        this.lebar = lebar;
        this.tinggi = tinggi;
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

    public Double tinggiGet() {
        return tinggi;
    }

    public void tinggiSet(Double tinggi) {
        this.tinggi = tinggi;
    }

    @Override
    public Double hitungLuasPermukaan() {
        return 2 * (panjang * lebar + panjang * tinggi + tinggi * lebar);
    }

    @Override
    public Double hitungVolume() {
        return panjang * lebar * tinggi;
    }

    public void 
}