package nusazoo.model;

public abstract class AirAnimal extends Animal {
    private double wingSpan;

    protected AirAnimal(String name, int age, String food, double wingSpan) {
        super(name, age, food);
        setWingSpan(wingSpan);
    }

    public double getWingSpan() { return wingSpan; }

    public final void setWingSpan(double w) {
        if (w < 0) throw new InvalidInputException("Bentang sayap tidak boleh negatif");
        this.wingSpan = w;
    }

    @Override
    public String printData() {
        return super.printData() + "\nWing Span   : " + wingSpan + " meter";
    }
}
