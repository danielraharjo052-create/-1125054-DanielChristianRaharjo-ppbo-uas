package nusazoo.model;

/** Business logic for animal activities (overloaded feed methods). */
public class ZooKeeper {
    public boolean feed(Animal animal) {
        return feed(animal, 1);
    }

    public boolean feed(Animal animal, int portions) {
        if (portions <= 0) throw new InvalidInputException("Jumlah porsi harus lebih dari 0");
        if (!(animal instanceof Feedable feedable)) return false;
        for (int i = 0; i < portions; i++) feedable.eat();
        return true;
    }

    /** Returns null when the animal cannot move. */
    public String describeMove(Animal animal) {
        return animal instanceof Movable m ? m.move() : null;
    }
}
