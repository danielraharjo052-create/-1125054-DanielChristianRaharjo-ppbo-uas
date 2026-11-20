package nusazoo.model;

public class Mouse extends LandAnimal implements Movable, Feedable {
    public Mouse(String name, int age, String food, int legs) {
        super(name, age, food, legs);
    }

    @Override
    public String printSound() {
        return "Cit Cit / Squeak";
    }

    @Override
    public String move() {
        return "Mouse is scurrying.";
    }

    @Override
    public void eat() {
        System.out.println("Mouse is eating " + getFood() + ".");
    }
}
