package model;

public abstract class Object2D extends GObject {
    public Object2D(String label) {
        super(label);
    }

    public abstract Double hitungLuas();

    public abstract Double hitungKeliling();
}