package model;

public abstract class GObject {

    private String label;

    public GObject(String label) {
        this.label = label;
    }

    public abstract String[][] getKeyValueDetails()
}