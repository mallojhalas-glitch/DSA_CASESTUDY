class AVLNode {
    int key;
    AVLNode left, right;
    int height;

    AVLNode(int key) {
        this.key = key;
        height = 1;
    }
}

public class CO1 {

    // Get height of node
    static int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Get balance factor
    static int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Update height
    static void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // Right Rotation
    static AVLNode rotateRight(AVLNode y) {

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left Rotation
    static AVLNode rotateLeft(AVLNode x) {

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Insert node
    static AVLNode insert(AVLNode node, int key) {

        // Normal BST insertion
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);

        else if (key > node.key)
            node.right = insert(node.right, key);

        else
            return node;

        // Update height
        updateHeight(node);

        // Get balance factor
        int balance = getBalance(node);

        // LL Case
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);

        // RR Case
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR Case
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL Case
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Find minimum node
    static AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    // Delete node
    static AVLNode deleteNode(AVLNode root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);

        else if (key > root.key)
            root.right = deleteNode(root.right, key);

        else {

            // One child or no child
            if ((root.left == null) || (root.right == null)) {

                AVLNode temp;

                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                // No child
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }

            } else {

                // Node with two children
                AVLNode temp = minValueNode(root.right);

                root.key = temp.key;

                root.right = deleteNode(root.right, temp.key);
            }
        }

        // If tree had only one node
        if (root == null)
            return root;

        // Update height
        updateHeight(root);

        // Get balance factor
        int balance = getBalance(root);

        // LL Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rotateRight(root);

        // LR Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // RR Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return rotateLeft(root);

        // RL Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    // Inorder traversal
    static void inorder(AVLNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }

    public static void main(String[] args) {

        AVLNode root = null;

        int packageIDs[] = {
                110, 125, 135, 142, 155,
                168, 176, 184, 193, 205,
                218, 226, 240
        };

        System.out.println("INSERTING PACKAGE IDs:");

        for (int id : packageIDs) {
            root = insert(root, id);
            System.out.println("Inserted: " + id);
        }

        System.out.println("\nINORDER TRAVERSAL OF AVL TREE:");
        inorder(root);

        // Deletions
        System.out.println("\n\nDELETING PACKAGE IDs:");

        root = deleteNode(root, 125);
        System.out.println("Deleted: 125");

        root = deleteNode(root, 193);
        System.out.println("Deleted: 193");

        root = deleteNode(root, 168);
        System.out.println("Deleted: 168");

        System.out.println("\nFINAL AVL TREE AFTER DELETIONS:");
        inorder(root);

        System.out.println("\n\nAVL Tree operations completed successfully.");
    }
}