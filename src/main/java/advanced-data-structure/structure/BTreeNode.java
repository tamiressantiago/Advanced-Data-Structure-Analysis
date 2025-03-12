class BTreeNode {
    int[] keys;
    int t;
    BTreeNode[] children;
    int n;
    boolean leaf;

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new int[2 * t - 1];
        children = new BTreeNode[2 * t];
        n = 0;
    }

    public BTreeNode search(int key) {
        int i = 0;
        while (i < n && key > keys[i])
            i++;

        if (i < n && keys[i] == key)
            return this;

        if (leaf)
            return null;

        return children[i].search(key);
    }

    public void insertNonFull(int key) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && key < keys[i]) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            n++;
        } else {
            while (i >= 0 && key < keys[i])
                i--;
            i++;

            if (children[i].n == 2 * t - 1) {
                splitChild(i, children[i]);
                if (key > keys[i])
                    i++;
            }
            children[i].insertNonFull(key);
        }
    }

    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++)
            z.keys[j] = y.keys[j + t];

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--)
            children[j + 1] = children[j];

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--)
            keys[j + 1] = keys[j];

        keys[i] = y.keys[t - 1];
        n++;
    }

    public void remove(int key) {
        int idx = 0;
        while (idx < n && keys[idx] < key)
            idx++;

        if (idx < n && keys[idx] == key) {
            if (leaf) {
                for (int i = idx; i < n - 1; i++)
                    keys[i] = keys[i + 1];
                n--;
            } else {
                if (children[idx].n >= t) {
                    keys[idx] = getPredecessor(idx);
                    children[idx].remove(keys[idx]);
                } else if (children[idx + 1].n >= t) {
                    keys[idx] = getSuccessor(idx);
                    children[idx + 1].remove(keys[idx]);
                } else {
                    merge(idx);
                    children[idx].remove(key);
                }
            }
        } else if (!leaf) {
            boolean isLastChild = (idx == n);
            if (children[idx].n < t)
                fill(idx);
            if (isLastChild && idx > n)
                children[idx - 1].remove(key);
            else
                children[idx].remove(key);
        }
    }
    

    private int getPredecessor(int idx) {
        BTreeNode cur = children[idx];
        while (!cur.leaf)
            cur = cur.children[cur.n];
        return cur.keys[cur.n - 1];
    }

    private int getSuccessor(int idx) {
        BTreeNode cur = children[idx + 1];
        while (!cur.leaf)
            cur = cur.children[0];
        return cur.keys[0];
    }

    private void merge(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[t - 1] = keys[idx];

        for (int i = 0; i < sibling.n; i++)
            child.keys[i + t] = sibling.keys[i];

        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++)
                child.children[i + t] = sibling.children[i];
        }

        for (int i = idx; i < n - 1; i++)
            keys[i] = keys[i + 1];

        for (int i = idx + 1; i < n; i++)
            children[i] = children[i + 1];

        child.n += sibling.n + 1;
        n--;
    }

    private void fill(int idx) {
        if (idx != 0 && children[idx - 1].n >= t)
            borrowFromPrev(idx);
        else if (idx != n && children[idx + 1].n >= t)
            borrowFromNext(idx);
        else {
            if (idx != n)
                merge(idx);
            else
                merge(idx - 1);
        }
    }

    private void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];

        for (int i = child.n - 1; i >= 0; i--)
            child.keys[i + 1] = child.keys[i];

        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--)
                child.children[i + 1] = child.children[i];
        }

        child.keys[0] = keys[idx - 1];

        if (!child.leaf)
            child.children[0] = sibling.children[sibling.n];

        keys[idx - 1] = sibling.keys[sibling.n - 1];
        child.n++;
        sibling.n--;
    }

    private void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[child.n] = keys[idx];

        if (!child.leaf)
            child.children[child.n + 1] = sibling.children[0];

        keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.n; i++)
            sibling.keys[i - 1] = sibling.keys[i];

        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++)
                sibling.children[i - 1] = sibling.children[i];
        }

        child.n++;
        sibling.n--;
    }

    public void printInOrder() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf)
                children[i].printInOrder();
            System.out.print(keys[i] + " ");
        }
        if (!leaf)
            children[i].printInOrder();
    }
}
