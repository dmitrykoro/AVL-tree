import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {

    private AVL tr = new AVL();
    private int size = 1000000;

    @org.junit.jupiter.api.Test
    void size() {
        addManyItems(size);
        assertEquals(size, tr.size());
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        addManyItems(size);
        tr.clear();
        assertTrue(tr.isEmpty());
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void contains() {
        addManyItems(size);
        assertTrue(tr.contains(1));
        assertTrue(tr.contains(2));
        assertTrue(tr.contains(100));
        assertTrue(tr.contains(999784));
        assertFalse(tr.contains(1000001));
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void toArray() {
        addManyItems(size);
        tr.toArray();
        Object[] testArr = new Object[size];
        for (int i = 0; i < size; i++) {
            testArr[i] = i;
        }
        assertArrayEquals(testArr, tr.toArray());
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void add() {
        tr.add("Hello");
        tr.add("My");
        tr.add("Name");
        tr.add("Is");
        assertTrue(tr.contains("Hello"));
        assertTrue(tr.contains("My"));
        assertTrue(tr.contains("Name"));
        assertTrue(tr.contains("Is"));
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void remove() {
        addManyItems(5);
        tr.remove(4);
        tr.remove(3);
        assertFalse(tr.contains(4));
        assertFalse(tr.contains(3));
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void addAll() {
        tr.addAll(Arrays.asList(1, 100, 54, 53, 20, 11, 4));
        assertTrue(tr.contains(1));
        assertTrue(tr.contains(100));
        assertTrue(tr.contains(53));
        assertTrue(tr.contains(20));
        assertTrue(tr.contains(11));
        assertTrue(tr.contains(4));
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void clear() {
        tr.addAll(Arrays.asList(1, 100, 54, 53, 20, 11, 4));
        tr.clear();
        assertTrue(tr.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        addManyItems(size);
        tr.removeAll(Arrays.asList(111, 1000, 1001, 1010, 1011, 1100));
        assertFalse(tr.contains(111));
        assertFalse(tr.contains(1000));
        assertFalse(tr.contains(1001));
        assertFalse(tr.contains(1010));
        assertFalse(tr.contains(1011));
        assertFalse(tr.contains(1100));
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void retainAll() {
        tr.addAll(Arrays.asList(4, 10, 5, 3, 8, 3, 4));
        tr.retainAll(Arrays.asList(5));
        assertTrue(tr.contains(5));
        assertFalse(tr.containsAll(Arrays.asList(4, 10, 3, 8, 3, 4)));
        tr.clear();
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        int sz = 1000;
        addManyItems(sz);
        Object[] testArr = new Object[sz];
        for (int i = 0; i < sz; i++) {
            testArr[i] = i;
        }
        assertTrue(tr.containsAll(Arrays.asList(testArr)));
        assertFalse(tr.containsAll(Arrays.asList(testArr, -5)));
        tr.clear();
    }

    private void addManyItems(int size) {
        for (int i = 0; i < size; i++) {
            tr.add(i);
        }
    }
}