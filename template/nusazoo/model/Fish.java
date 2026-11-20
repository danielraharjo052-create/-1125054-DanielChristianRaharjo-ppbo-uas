package nusazoo.model;

public class Fish extends SeaAnimal implements Movable, Feedable {
    public Fish(String name, int age, String food, double maxDepth) {
        super(name, age, food, maxDepth);
    }

    @Override
    public String printSound() {
        return "Blub Blub";
    }

    @Override
    public String move() {
        return "Fish is swimming rapidly.";
    }

    @Override
    public void eat() {
        System.out.println("Fish is eating " + getFood() + ".");
    }
}
