package nusazoo.view;

import nusazoo.model.*;

public class AnimalDataView extends DataView<Animal> {
    public AnimalDataView(ConsoleIO io) {
        super(io);
    }

    @Override
    protected String getPrimaryKeyValue(Animal data) {
        return data.getName();
    }

    @Override
    protected String[] getFields() {
        return new String[]{"type", "name", "age", "food", "extra(legs/depth/wing)"};
    }

    @Override
    protected Animal convertValuesToModel(String[] v) {
        String name = v[1];
        int age = Integer.parseInt(v[2]);
        String food = v[3];
        String type = v[0].trim().toLowerCase();
        try {
            return switch (type) {
                case "dog" -> new Dog(name, age, food, Integer.parseInt(v[4]));
                case "cat" -> new Cat(name, age, food, Integer.parseInt(v[4]));
                case "mouse" -> new Mouse(name, age, food, Integer.parseInt(v[4]));
                case "robotdinosaur" -> new RobotDinosaur(name, age, food, Integer.parseInt(v[4]));
                case "fish" -> new Fish(name, age, food, Double.parseDouble(v[4]));
                case "shark" -> new Shark(name, age, food, Double.parseDouble(v[4]));
                case "whale" -> new Whale(name, age, food, Double.parseDouble(v[4]));
                case "eagle" -> new Eagle(name, age, food, Double.parseDouble(v[4]));
                case "parrot" -> new Parrot(name, age, food, Double.parseDouble(v[4]));
                case "duck" -> new Duck(name, age, food, Double.parseDouble(v[4]));
                default -> throw new InvalidInputException("Tipe hewan tidak dikenal: " + v[0]);
            };
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Nilai extra harus berupa angka");
        }
    }

    @Override
    protected String[] convertModelToValues(Animal a) {
        String extra;
        if (a instanceof LandAnimal l) extra = String.valueOf(l.getNumberOfLegs());
        else if (a instanceof SeaAnimal s) extra = String.valueOf(s.getMaximumDepth());
        else if (a instanceof AirAnimal w) extra = String.valueOf(w.getWingSpan());
        else extra = "-";
        return new String[]{a.getClass().getSimpleName(), a.getName(), String.valueOf(a.getAge()), a.getFood(), extra};
    }
}
