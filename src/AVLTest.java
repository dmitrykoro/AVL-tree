import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {

    private int size = 1000000;

    @org.junit.jupiter.api.Test
    void size() {
        AVL tr = new AVL<>();
        addManyItems(size, tr);
        assertEquals(size, tr.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        AVL tr = new AVL<>();
        addManyItems(size, tr);
        tr.clear();
        assertTrue(tr.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void contains() {
        AVL tr = new AVL<>();
        addManyItems(size, tr);
        assertTrue(tr.contains(1));
        assertTrue(tr.contains(2));
        assertTrue(tr.contains(100));
        assertTrue(tr.contains(999784));
        assertFalse(tr.contains(size + 1));
    }

    @org.junit.jupiter.api.Test
    void iterator() {

        AVL tr = new AVL<>();
        addManyItems(size, tr);
        Object[] arr = new Object[size];

        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }

        assertArrayEquals(arr, tr.toArray());
    }

    @org.junit.jupiter.api.Test
    void toArray() {

        AVL tr = new AVL<>();
        addManyItems(size, tr);
        tr.toArray();
        Object[] testArr = new Object[size];

        for (int i = 0; i < size; i++) {
            testArr[i] = i;
        }

        assertArrayEquals(testArr, tr.toArray());
}

    @org.junit.jupiter.api.Test
    void add() {
        AVL tr = new AVL<>();
        tr.add("Hello");
        tr.add("My");
        tr.add("Name");
        tr.add("Is");
        assertTrue(tr.contains("Hello"));
        assertTrue(tr.contains("My"));
        assertTrue(tr.contains("Name"));
        assertTrue(tr.contains("Is"));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        AVL tr = new AVL<>();
        addManyItems(5, tr);
        tr.remove(4);
        tr.remove(3);
        assertFalse(tr.contains(4));
        assertFalse(tr.contains(3));
    }

    @org.junit.jupiter.api.Test
    void addAll() {
        AVL tr = new AVL<>();
        tr.addAll(Arrays.asList(1, 100, 54, 53, 20, 11, 4));
        assertTrue(tr.contains(1));
        assertTrue(tr.contains(100));
        assertTrue(tr.contains(53));
        assertTrue(tr.contains(20));
        assertTrue(tr.contains(11));
        assertTrue(tr.contains(4));
    }

    @org.junit.jupiter.api.Test
    void clear() {
        AVL tr = new AVL<>();
        tr.addAll(Arrays.asList(1, 100, 54, 53, 20, 11, 4));
        tr.clear();
        assertTrue(tr.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        AVL tr = new AVL<>();
        addManyItems(size, tr);
        tr.removeAll(Arrays.asList(111, 1000, 1001, 1010, 1011, 1100));
        assertFalse(tr.contains(111));
        assertFalse(tr.contains(1000));
        assertFalse(tr.contains(1001));
        assertFalse(tr.contains(1010));
        assertFalse(tr.contains(1011));
        assertFalse(tr.contains(1100));
    }

    @org.junit.jupiter.api.Test
    void retainAll() {
        AVL tr = new AVL<>();
        tr.addAll(Arrays.asList(4, 10, 5, 3, 8, 3, 4));
        tr.retainAll(Arrays.asList(5));
        assertTrue(tr.contains(5));
        assertFalse(tr.containsAll(Arrays.asList(4, 10, 3, 8, 3, 4)));
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        AVL tr = new AVL<>();

        int sz = 1000;
        addManyItems(sz, tr);
        Object[] testArr = new Object[sz];

        for (int i = 0; i < sz; i++) {
            testArr[i] = i;
        }

        assertTrue(tr.containsAll(Arrays.asList(testArr)));
        assertFalse(tr.containsAll(Arrays.asList(testArr, -5)));
        tr.clear();
    }

    private void addManyItems(int size, AVL tr) {
        for (int i = 0; i < size; i++) {
            tr.add(i);
        }
    }
}