package nusazoo.model;

public class Cat extends LandAnimal implements Movable, Feedable {
    public Cat(String name, int age, String food, int legs) {
        super(name, age, food, legs);
    }

    @Override
    public String printSound() {
        return "Meow Meow";
    }

    @Override
    public String move() {
        return "Cat is stalking and walking softly.";
    }

    @Override
    public void eat() {
        System.out.println("Cat is eating " + getFood() + ".");
    }
}
