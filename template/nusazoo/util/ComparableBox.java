package nusazoo.util;

/** The Comparable bound lives here (not on Box), so plain Box<T> accepts any type. */
public class ComparableBox<T extends Comparable<? super T>> extends Box<T> implements Comparable<ComparableBox<T>> {
    public ComparableBox(T content) {
        super(content);
    }

    @Override
    public int compareTo(ComparableBox<T> other) {
        return getContent().compareTo(other.getContent());
    }
}
