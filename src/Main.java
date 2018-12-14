import java.util.*;

public class Main<T extends Comparable<T>> implements Set<T> {

    static Main tree = new Main();

    private Node<T> root;

    private static class Node<T extends Comparable<T>> {

        T key;

        int height;

        private Node<T> leftChild;
        private Node<T> rightChild;

        Node(T key, Node<T> leftChild, Node<T> rightChild) {
            this.key = key;
            this.rightChild = rightChild;
            this.leftChild = leftChild;
        }

    }

    @Override
    public int size() {
        Iterator itr = tree.iterator();
        int size = 0;
        while (itr.hasNext()) {
            size++;
            itr.next();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object key) {
        for (Object aTree : tree) {
            if (aTree.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SubIterator();
    }

    private class SubIterator implements Iterator<T> {

        Node current = root;
        Stack<Node> stack;

        public SubIterator() {
            stack = new Stack<>();

            while (current != null) {
                stack.push(current);
                current = current.leftChild;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public T next() {
            Node curr = stack.pop();
            Object key = curr.key;
            if (curr.rightChild != null) {
                curr = curr.rightChild;
                while (curr != null) {
                    stack.push(curr);
                    curr = curr.leftChild;
                }
            }
            return (T) key;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[tree.size()];
        Iterator itr = tree.iterator();
        int i = 0;
        while (itr.hasNext()) {
            result[i] = itr.next();
            i++;
        }
        return result;
    }

    @Override
    public boolean add(T key) {
        root = insert(key, root);
        return false;
    }

    private Node insert(T key, Node current) {
        if (current == null)
            current = new Node(key, null, null);
         else if (key.compareTo((T) current.key) < 0) {
            current.leftChild = insert(key, current.leftChild);
            current = balance(current, key);
        } else if (key.compareTo((T) current.key) > 0) {
            current.rightChild = insert(key, current.rightChild);
            current = balance(current, key);
        }
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        return current;
    }

    private Node balance(Node current, T key) {
        if (height(current.leftChild) - height(current.rightChild) == 2) {
            if (key.compareTo((T) current.leftChild.key) < 0) {
                current = smallLeftRotate(current);
            } else {
                current = bigLeftRotate(current);
            }
        } else if (height(current.rightChild) - height(current.leftChild) == 2) {
            if (key.compareTo((T) current.rightChild.key) > 0) {
                current = smallRightRotate(current);
            } else {
                current = bigRightRotate(current);
            }
        }

        return current;
    }

    private Node smallLeftRotate(Node current) {
        Node currLeftChild = current.leftChild;  //
        current.leftChild = currLeftChild.rightChild;
        currLeftChild.rightChild = current;
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        currLeftChild.height = Math.max(height(currLeftChild.leftChild), current.height) + 1;
        return currLeftChild;
    }

    private Node smallRightRotate(Node current) {
        Node currRightChild = current.rightChild;
        current.rightChild = currRightChild.leftChild;
        currRightChild.leftChild = current;
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        currRightChild.height = Math.max(height(currRightChild.rightChild), current.height) + 1;
        return currRightChild;
    }

    private Node bigLeftRotate(Node current) {
        current.leftChild = smallRightRotate(current.leftChild);
        return smallLeftRotate(current);
    }

    private Node bigRightRotate(Node current) {
        current.rightChild = smallLeftRotate(current.rightChild);
        return smallRightRotate(current);
    }

    @Override
    public boolean remove(Object key) {
        root = remove(root, (T) key);
        return false;
    }

    private Node remove(Node current, T key) {

        if (current == null)
            return current;
        if (key.compareTo((T) current.key) < 0)
            current.leftChild = remove(current.leftChild, key);
        else if (key.compareTo((T) current.key) > 0)
            current.rightChild = remove(current.rightChild, key);
        else {
            if (current.leftChild == null || current.rightChild == null) {
                Node temp = null;
                if (temp == current.leftChild)
                    temp = current.rightChild;
                else
                    temp = current.leftChild;
                if (temp == null) {
                    temp = current;
                    current = null;
                } else
                    current = temp;
            } else {
                Node temp = minimumNode(current.rightChild);
                current.key = temp.key;
                current.rightChild = remove(current.rightChild, (T) temp.key);
            }
        }

        if (current == null)
            return current;

        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;

        current = balance(current, (T) current.key);

        return current;
    }

    private Node minimumNode(Node current) {
        while (current.leftChild != null)
            current = current.leftChild;
        return current;
    }

    @Override
    public boolean addAll(Collection c) {
        for (Iterator i = c.iterator(); i.hasNext(); ) {
            tree.add((Comparable) i.next());
        }
        return false;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean removeAll(Collection c) { //удаление всех коллекционных элементов из дерева
        Iterator i = c.iterator();
        while (i.hasNext()) {
            Object tmp = i.next();
            if (tree.contains(tmp))
                tree.remove(tmp);
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection c) { //удаление всех, кроме тех что в коллекции
        Iterator i = tree.iterator();
        while (i.hasNext()) {
            Object tmp = i.next();
            if (!c.contains(tmp))
                tree.remove(tmp);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection c) { //есть ли в дереве все, которые есть в коллекции
        //TODO
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {  //один элемент в массив
        //TODO
        return new Object[0];
    }

    private int height(Node current) {
        return current == null ? -1 : current.height;
    }

    public static void main(String[] args) {


        tree.add(15);
        tree.add(14);
        tree.add(8);
        tree.add(1);
        tree.add(0);
        tree.add(5);
        tree.add(20);

        Object arr = tree.toArray();
        int sz = tree.size();

        tree.remove(0);
        tree.remove(15);
        tree.remove(14);

        sz = tree.size();

        tree.addAll(Arrays.asList(14, 45, 12, 17, 18, 19, 21, 39, 140));

        sz = tree.size();

        //tree.addAll()

        boolean check = tree.contains(0);
        check = tree.contains(14);
        check = tree.contains(45);
        check = tree.contains(12);

       tree.removeAll(Arrays.asList(14, 45, 12, 17, 18, 19, 21, 39, 140));

        sz = tree.size();


        check = tree.contains(14);
        check = tree.contains(45);
        check = tree.contains(12);

        tree.retainAll(Arrays.asList(5));

        arr = tree.toArray();


    }
}
