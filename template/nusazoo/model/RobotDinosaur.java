package nusazoo.model;

/** Animatronic attraction: can move, but cannot be fed (only Movable). */
public class RobotDinosaur extends LandAnimal implements Movable {
    public RobotDinosaur(String name, int age, String food, int legs) {
        super(name, age, food, legs);
    }

    @Override
    public String printSound() {
        return "*mechanical roar*";
    }

    @Override
    public String move() {
        return "RobotDinosaur is stomping on hydraulic legs.";
    }
}
