package nusazoo.model;

public abstract class SeaAnimal extends Animal {
    private double maximumDepth;

    protected SeaAnimal(String name, int age, String food, double maximumDepth) {
        super(name, age, food);
        setMaximumDepth(maximumDepth);
    }

    public double getMaximumDepth() { return maximumDepth; }

    public final void setMaximumDepth(double d) {
        if (d < 0) throw new InvalidInputException("Kedalaman tidak boleh negatif");
        this.maximumDepth = d;
    }

    @Override
    public String printData() {
        return super.printData() + "\nMax Depth   : " + maximumDepth + " meter";
    }
}
