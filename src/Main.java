
public class Main {

    private Node root;

    private static class Node {

        int key;

        int height;

        private Node parent;
        private Node leftChild;
        private Node rightChild;

        public void displayNode() {
            System.out.println("{" + key + "} ");
        }

        Node(int key, Node parent) {
            this.key = key;
            this.parent = parent;
        }

    }

    private int height (Node current) {
        return current == null ? -1 : current.height;
    }

    private void insert (int key) {
        root = insert(key, root);
    }

    private Node insert (int key, Node current) {
        if (current == null) {
            current = new Node(key, null);
        }
        else if (key < current.key) {
            current.leftChild = insert(key, current.leftChild);
            current = balance(current, key);
        }
        else if (key > current.key){
            current.rightChild = insert(key, current.rightChild);
            current = balance(current, key);
        }
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        return current;
    }

    private Node balance (Node current, int key) {
        if (height(current.leftChild) - height(current.rightChild) == 2) {
            if (key < current.leftChild.key) {
                current = smallLeftRotate(current);
            }
            else {
                current = bigLeftRotate(current);
            }
        }
        else if (height(current.rightChild) - height(current.leftChild) == 2) {
            if (key > current.rightChild.key) {
                current = smallRightRotate(current);
            }
            else {
                current = bigRightRotate(current);
            }
        }

        return current;
    }

    private Node smallLeftRotate (Node current) {
        Node currLeftChild = current.leftChild;
        current.leftChild = currLeftChild.rightChild;
        currLeftChild.rightChild = current;
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        currLeftChild.height = Math.max(height(currLeftChild.leftChild), current.height) + 1;
        return currLeftChild;
    }

    private Node smallRightRotate (Node current) {
        Node currRightChild = current.rightChild;
        current.rightChild = currRightChild.leftChild;
        currRightChild.leftChild = current;
        current.height = Math.max(height(current.leftChild), height(current.rightChild)) + 1;
        currRightChild.height = Math.max(height(currRightChild.rightChild), current.height) + 1;
        return currRightChild;
    }

    private Node bigLeftRotate (Node current) {
        current.leftChild = smallRightRotate(current.leftChild);
        return smallLeftRotate(current);
    }

    private Node bigRightRotate (Node current) {
        current.rightChild = smallLeftRotate(current.rightChild);
        return smallRightRotate(current);
    }

    public static void main(String[] args) {
        Main tree = new Main();

        tree.insert(15);
        tree.insert(14);
        tree.insert(25);
        tree.insert(3);
        tree.insert(8);
        tree.insert(7);
        tree.insert(6);

    }
}
