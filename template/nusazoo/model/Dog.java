package nusazoo.model;

public class Dog extends LandAnimal implements Movable, Feedable {
    public Dog(String name, int age, String food, int legs) {
        super(name, age, food, legs);
    }

    @Override
    public String printSound() {
        return "Woof Woof";
    }

    @Override
    public String move() {
        return "Dog is running.";
    }

    @Override
    public void eat() {
        System.out.println("Dog is eating " + getFood() + ".");
    }
}
