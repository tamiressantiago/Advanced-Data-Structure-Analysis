package structure;
public class BTree {
    private BTreeNode root;
    private int t;

    public BTree(int t) {
        this.t = t;
        root = null;
    }

    public BTreeNode search(int key) {
        return (root == null) ? null : root.search(key);
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = key;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode newRoot = new BTreeNode(t, false);
                newRoot.children[0] = root;
                newRoot.splitChild(0, root);
                int i = (newRoot.keys[0] < key) ? 1 : 0;
                newRoot.children[i].insertNonFull(key);
                root = newRoot;
            } else {
                root.insertNonFull(key);
            }
        }
    }

    public void remove(int key) {
        if (root != null)
            root.remove(key);
    }

    public void printBTree() {
        if (root != null)
            root.printInOrder();
        System.out.println();
    }
    
}
