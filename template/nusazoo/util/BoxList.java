package nusazoo.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/** Hand-written List<T> backed by a manually resized Object[] (no ArrayList inside). */
public class BoxList<T> implements List<T> {
    private Object[] data = new Object[4];
    private int size = 0;

    private void ensureCapacity() {
        if (size == data.length) data = Arrays.copyOf(data, data.length * 2);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override public int size() { return size; }
    @Override public boolean isEmpty() { return size == 0; }
    @Override public boolean contains(Object o) { return indexOf(o) >= 0; }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;
            private int last = -1;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                last = cursor++;
                return (T) data[last];
            }

            @Override
            public void remove() {
                if (last < 0) throw new IllegalStateException();
                BoxList.this.remove(last);
                cursor = last;
                last = -1;
            }
        };
    }

    @Override public Object[] toArray() { return Arrays.copyOf(data, size); }

    @Override
    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] a) {
        if (a.length < size) return (E[]) Arrays.copyOf(data, size, a.getClass());
        System.arraycopy(data, 0, a, 0, size);
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public boolean add(T t) {
        ensureCapacity();
        data[size++] = t;
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i < 0) return false;
        remove(i);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T old = (T) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return old;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) add(t);
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int i = index;
        for (T t : c) add(i++, t);
        return !c.isEmpty();
    }

    // Not needed by this system: deliberately unsupported.
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException("removeAll"); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException("retainAll"); }
    @Override public ListIterator<T> listIterator() { throw new UnsupportedOperationException("listIterator"); }
    @Override public ListIterator<T> listIterator(int index) { throw new UnsupportedOperationException("listIterator"); }
    @Override public List<T> subList(int from, int to) { throw new UnsupportedOperationException("subList"); }

    @Override
    public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = element;
        return old;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) if (Objects.equals(o, data[i])) return i;
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) if (Objects.equals(o, data[i])) return i;
        return -1;
    }

    public void printList() {
        PrintUtil.printList(Arrays.asList(toArray()));
    }

    /** Safe for mixed element types: falls back to type name then text when not mutually comparable. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void printListSorted() {
        Object[] copy = Arrays.copyOf(data, size);
        Arrays.sort(copy, (a, b) -> {
            if (a == null || b == null) return a == b ? 0 : (a == null ? -1 : 1);
            if (a instanceof Comparable) {
                try {
                    return ((Comparable) a).compareTo(b);
                } catch (ClassCastException ignored) {
                    // not mutually comparable: use fallback below
                }
            }
            int c = a.getClass().getSimpleName().compareTo(b.getClass().getSimpleName());
            return c != 0 ? c : String.valueOf(a).compareTo(String.valueOf(b));
        });
        PrintUtil.printList(Arrays.asList(copy));
    }
}
