public abstract class GObject implements KeyValueDetailableI {

    private String label;

    public GObject(String label) {
        this.label = label;
    }

    public String labelGet() {
        return label;
    }

    public void labelSet(String label) {
        this.label = label;
    }

    @Override
    public String[][] getKeyValueDetails() {
        return new String[][] {
            {"label", label}
        };
    }
}