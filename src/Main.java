import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class Main implements Set {

    private Node root;

    private static class Node<T extends Comparable> {

        T key;

        int height;

        private Node leftChild;
        private Node rightChild;

        Node(T key, Node leftChild, Node rightChild) {
            this.key = key;
            this.rightChild = rightChild;
            this.leftChild = leftChild;
        }

    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node current) {
        if (current == null)
            return 0;
        else {
            int size = 1;
            size += size(current.leftChild);
            size += size(current.rightChild);
            return size;
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object key) {
        return contains(root, key);
    }

    private boolean contains(Node current, Object key) {
        boolean found = false;
        while (!found && current != null) {
            Object currKey = current.key;
            if (((Comparable) key).compareTo(currKey) < 0)
                current = current.leftChild;
            else if (((Comparable) key).compareTo(currKey) > 0)
                current = current.rightChild;
            else {
                return true;
            }
            found = contains(current, key);
        }
        return found;
    }

    @Override
    public Iterator iterator() {
        return new subIterator();
    }

    private class subIterator implements Iterator {

        Node current = root;
        Stack<Node> stack;

        public subIterator() {
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
        public Object next() {
            Node curr = stack.pop();
            Object key = curr.key;
            if (curr.rightChild != null) {
                curr = curr.rightChild;
                while (curr != null) {
                    stack.push(curr);
                    curr = curr.leftChild;
                }
            }
            return key;
        }
    }

    @Override
    public Object[] toArray() {
        //TODO
        return new Object[0];
    }

    @Override
    public boolean add(Object key) {
        root = insert(key, root);
        return false;
    }

    private Node insert(Object key, Node current) {
        if (current == null)
            current = new Node((Comparable) key, null, null);
         else if (((Comparable) key).compareTo(current.key) < 0) {
            current.leftChild = insert(key, current.leftChild);
            current = balance(current, key);
        } else if (((Comparable) key).compareTo(current.key) > 0) {
            current.rightChild = insert(key, current.rightChild);
            current = balance(current, key);
        }
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        return current;
    }

    private Node balance(Node current, Object key) {
        if (height(current.leftChild) - height(current.rightChild) == 2) {
            if (((Comparable) key).compareTo(current.leftChild.key) < 0) {
                current = smallLeftRotate(current);
            } else {
                current = bigLeftRotate(current);
            }
        } else if (height(current.rightChild) - height(current.leftChild) == 2) {
            if (((Comparable) key).compareTo(current.rightChild.key) > 0) {
                current = smallRightRotate(current);
            } else {
                current = bigRightRotate(current);
            }
        }

        return current;
    }

    private Node smallLeftRotate(Node current) {
        Node currLeftChild = current.leftChild;
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
        root = remove(root, key);
        return false;
    }

    private Node remove(Node current, Object key) {

        if (current == null)
            return current;
        if (((Comparable) key).compareTo(current.key) < 0)
            current.leftChild = remove(current.leftChild, key);
        else if (((Comparable) key).compareTo(current.key) > 0)
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
                current.rightChild = remove(current.rightChild, temp.key);
            }
        }

        if (current == null)
            return current;

        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;

        balance(current, current.key);

        return current;
    }

    private Node minimumNode(Node current) {
        while (current.leftChild != null)
            current = current.leftChild;
        return current;
    }

    @Override
    public boolean addAll(Collection c) {
        //TODO
        return false;
    }

    @Override
    public void clear() {
        //TODO
    }

    @Override
    public boolean removeAll(Collection c) {
        //TODO
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        //TODO
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        //TODO
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        //TODO
        return new Object[0];
    }

    private int height(Node current) {
        return current == null ? -1 : current.height;
    }

    public static void main(String[] args) {
        Main tree = new Main();

        tree.add(15);
        tree.add(14);
        tree.add(8);
        tree.add(1);
        tree.add(0);
        tree.add(5);
        tree.add(20);

        int sz = tree.size();

        tree.remove(5);
        tree.remove(0);
        tree.remove(15);

        sz = tree.size();

        boolean check = tree.contains(5);
        check = tree.contains(0);
        check = tree.contains(15);


    }
}
