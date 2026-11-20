package nusazoo.model;

public abstract class LandAnimal extends Animal {
    private int numberOfLegs;

    protected LandAnimal(String name, int age, String food, int numberOfLegs) {
        super(name, age, food);
        setNumberOfLegs(numberOfLegs);
    }

    public int getNumberOfLegs() { return numberOfLegs; }

    public final void setNumberOfLegs(int n) {
        if (n < 0) throw new InvalidInputException("Jumlah kaki tidak boleh negatif");
        this.numberOfLegs = n;
    }

    @Override
    public String printData() {
        return super.printData() + "\nLegs        : " + numberOfLegs;
    }
}
