
public class Main {

    public class Node {

        int key;
        double data;
        public Node leftChild;
        public Node rightChild;

        public void displayNode() {
            System.out.println("{" + key + ", " + data + "} ");
        }

    }

    public class Tree {
        private Node root;
        public Tree() {
            root = null;
        }
    }
}
