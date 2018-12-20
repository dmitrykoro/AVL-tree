import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import static java.lang.StrictMath.max;

public class AVL<T extends Comparable<T>> implements Set<T> {

    private Node<T> root;

    AVL() {
        this.root = null;
    }

    AVL(Node root) {
        this.root = root;
    }

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
        Iterator itr = this.iterator();
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
        for (Object aTree : this) {
            if (aTree.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SubIterator();
    }

    private class SubIterator<T> implements Iterator<T> {

        Node current = root;
        Stack<Node> stack;

        public SubIterator() {
            stack = new Stack<Node>();

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
        Object[] result = new Object[this.size()];
        Iterator itr = this.iterator();
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


    Node insert(T key, Node current) {
        if (current == null)
            return new Node(key, null, null);

        if (key.compareTo((T) current.key) < 0)
            current.leftChild = insert(key, current.leftChild);
        else if (key.compareTo((T) current.key) > 0)
            current.rightChild = insert(key, current.rightChild);
        else
            return current;

        current.height = 1 + max(height(current.leftChild),
                height(current.rightChild));

        int balance = getBalance(current);

        if (balance < -1 && key.compareTo((T) current.rightChild.key) > 0)
            return smallLeftRotate(current);
        if (balance > 1 && key.compareTo((T) current.leftChild.key) < 0)
            return smallRightRotate(current);
        if (balance > 1 && key.compareTo((T) current.leftChild.key) > 0) {
            current.leftChild = smallLeftRotate(current.leftChild);
            return smallRightRotate(current);
        }
        if (balance < -1 && key.compareTo((T) current.rightChild.key) < 0) {
            current.rightChild = smallRightRotate(current.rightChild);
            return smallLeftRotate(current);
        }
        return current;
    }

    @Override
    public boolean remove(Object key) {
        root = remove(root, (T) key);
        return false;
    }

    Node remove(Node root, T key) {
        if (root == null)
            return root;
        if (key.compareTo((T) root.key) < 0)
            root.leftChild = remove(root.leftChild, key);
        else if (key.compareTo((T) root.key) > 0)
            root.rightChild = remove(root.rightChild, key);
        else {

            if ((root.leftChild == null) || (root.rightChild == null)) {
                Node tmp = null;
                if (tmp == root.leftChild)
                    tmp = root.rightChild;
                else
                    tmp = root.leftChild;
                if (tmp == null) {
                    root = null;
                } else
                    root = tmp;
            } else {

                Node tmp = minimumNode(root.rightChild);
                root.key = tmp.key;
                root.rightChild = remove(root.rightChild, (T) tmp.key);

            }
        }

        if (root == null)
            return root;

        root.height = max(height(root.leftChild), height(root.rightChild)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.leftChild) >= 0)
            return smallRightRotate(root);
        if (balance > 1 && getBalance(root.leftChild) < 0) {
            root.leftChild = smallLeftRotate(root.leftChild);
            return smallRightRotate(root);
        }
        if (balance < -1 && getBalance(root.rightChild) <= 0)
            return smallLeftRotate(root);
        if (balance < -1 && getBalance(root.rightChild) > 0) {
            root.rightChild = smallRightRotate(root.rightChild);
            return smallLeftRotate(root);
        }
        return root;
    }

    private Node smallRightRotate(Node current) {
        Node currLeft = current.leftChild;
        Node currRight = currLeft.rightChild;
        currLeft.rightChild = current;
        current.leftChild = currRight;
        current.height = max(height(current.leftChild), height(current.rightChild)) + 1;
        currLeft.height = max(height(currLeft.leftChild), height(currLeft.rightChild)) + 1;
        return currLeft;
    }

    private Node smallLeftRotate(Node current) {
        Node currRight = current.rightChild;
        Node currLeft = currRight.leftChild;
        currRight.leftChild = current;
        current.rightChild = currLeft;
        current.height = max(height(current.leftChild), height(current.rightChild)) + 1;
        currRight.height = max(height(currRight.leftChild), height(currRight.rightChild)) + 1;
        return currRight;
    }

    private int getBalance(Node current) {
        if (current == null)
            return 0;
        return height(current.leftChild) - height(current.rightChild);
    }

    private Node minimumNode(Node current) {
        while (current.leftChild != null)
            current = current.leftChild;
        return current;
    }

    @Override
    public boolean addAll(Collection c) {
        for (Iterator i = c.iterator(); i.hasNext(); )
            this.add((T) i.next());
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
            if (this.contains(tmp))
                this.remove(tmp);
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection c) { //удаление всех, кроме тех что в коллекции
        Iterator i = this.iterator();
        while (i.hasNext()) {
            Object tmp = i.next();
            if (!c.contains(tmp))
                this.remove(tmp);
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection c) { //есть ли в дереве все, которые есть в коллекции
        Iterator i = c.iterator();
        while (i.hasNext()) {
            Object tmp = i.next();
            if (!this.contains(tmp))
                return false;
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return a;
    }

    int height(Node current) {
        if (current == null)
            return 0;
        return current.height;
    }

}
