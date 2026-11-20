package nusazoo.model;

public class Shark extends SeaAnimal implements Movable, Feedable {
    public Shark(String name, int age, String food, double maxDepth) {
        super(name, age, food, maxDepth);
    }

    @Override
    public String printSound() {
        return "Silent Splash";
    }

    @Override
    public String move() {
        return "Shark is gliding through the deep ocean.";
    }

    @Override
    public void eat() {
        System.out.println("Shark is eating " + getFood() + ".");
    }
}
