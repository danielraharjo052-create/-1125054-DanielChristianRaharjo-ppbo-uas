package nusazoo.model;

/** Level 1 of the hierarchy. Natural order = name, case-insensitive. */
public abstract class Animal implements Comparable<Animal>, Printable {
    private String name;
    private int age;
    private String food;

    protected Animal(String name, int age, String food) {
        setName(name);
        setAge(age);
        setFood(food);
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getFood() { return food; }

    public final void setName(String name) {
        if (name == null || name.isBlank()) throw new InvalidInputException("Nama hewan tidak boleh kosong");
        this.name = name.trim();
    }

    public final void setAge(int age) {
        if (age < 0) throw new InvalidInputException("Umur tidak boleh negatif");
        this.age = age;
    }

    public final void setFood(String food) {
        if (food == null || food.isBlank()) throw new InvalidInputException("Makanan tidak boleh kosong");
        this.food = food.trim();
    }

    public abstract String printSound();

    public String printData() {
        return "Animal Type : " + getClass().getSimpleName()
                + "\nName        : " + name
                + "\nAge         : " + age + " tahun"
                + "\nFood        : " + food;
    }

    @Override
    public String getPrintDetail() {
        return name + " (" + getClass().getSimpleName() + ", " + age + " tahun)";
    }

    @Override
    public int compareTo(Animal other) {
        return name.compareToIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Animal a && a.name.equalsIgnoreCase(name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return getPrintDetail();
    }
}
