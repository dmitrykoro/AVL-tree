
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

    private void insert (int key) {
        root = insert(key, root);
    }

    private Node insert (int key, Node current) {
        if (current == null) {
            current = new Node(key, null);
            return current;
        }
        else if (key < root.key) {
            current.leftChild = insert(key, current.leftChild);
            current = balance(current, key);
        }
        else {
            current.rightChild = insert(key, current.rightChild);
            current = balance(current, key);
        }
        return current;
    }

    private Node balance (Node current, int key) {
        if (current.leftChild.height - current.rightChild.height == 2) {
            if (key < current.leftChild.key) {
                // TODO Small left rotate
            }
            else {
                // TODO Big left rotate
            }
        }
        else if (current.rightChild.height - current.leftChild.height == 2) {
            if (key > current.rightChild.key) {
                // TODO Small right rotate
            }
            else {
                // TODO Big right rotate
            }
        }
        return current;
    }

    private Node setHeight (Node current) {
        // TODO reheight
        return current;
    }

    public static void main(String[] args) {
        Main tree = new Main();

        tree.insert(1);
        tree.insert(2);
        tree.insert(0);
        tree.insert(-1);
        tree.insert(5);
        tree.insert(10);

    }
}
