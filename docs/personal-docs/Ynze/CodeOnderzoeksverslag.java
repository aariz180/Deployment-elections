// In dit voorbeeld laten we op een vereenvoudigde manier zien hoe een AVL-tree en een Red-Black tree
// na een invoegoperatie in balans worden gehouden. Let op: dit is geen volledige, production-ready code.
// Het doel is om op hoofdlijnen te illustreren hoe de balansbewaking in AVL en Red-Black Trees werkt.
// Voor een volledige implementatie zijn meer details en checks nodig.

class AVLNode {
    int key;
    AVLNode left, right;
    int height;
    
    AVLNode(int key) {
        this.key = key;
        height = 1; // Nieuwe knoop heeft beginhoogte 1
    }
}

class AVLTree {
    AVLNode root;
    
    // AVL Invoegen: eerst standaard BST-insert, daarna balans controleren.
    public void insert(int key) {
        root = insertRec(root, key);
    }
    
    private AVLNode insertRec(AVLNode node, int key) {
        // Standaard BST-insert
        if (node == null)
            return new AVLNode(key);
        if (key < node.key)
            node.left = insertRec(node.left, key);
        else if (key > node.key)
            node.right = insertRec(node.right, key);
        else
            return node; // Geen dubbele sleutels toevoegen
        
        // Hoogte updaten
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        // Balansfactor controleren
        int balance = getBalance(node);
        
        // Als de boom uit balans is, pas rotaties toe
        
        // Linkszwaar (LL)
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);
        
        // Rechtszwaar (RR)
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);
        
        // Links-Rechts (LR)
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        
        // Rechts-Links (RL)
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        
        return node;
    }
    
    int height(AVLNode n) {
        return (n == null) ? 0 : n.height;
    }
    
    int getBalance(AVLNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }
    
    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // Rotatie uitvoeren
        x.right = y;
        y.left = T2;
        
        // Hoogtes updaten
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        
        return x;
    }
    
    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // Rotatie uitvoeren
        y.left = x;
        x.right = T2;
        
        // Hoogtes updaten
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        
        return y;
    }
}

enum Color { RED, BLACK }

class RBNode {
    int key;
    Color color;
    RBNode left, right, parent;
    
    RBNode(int key) {
        this.key = key;
        this.color = Color.RED; // Nieuwe knopen zijn bij RB-insert eerst rood
    }
}

class RedBlackTree {
    private RBNode root;
    
    public void insert(int key) {
        RBNode newNode = new RBNode(key);
        root = bstInsert(root, newNode);
        fixInsertion(newNode);
    }
    
    // Standaard BST-insert voor RB-tree
    private RBNode bstInsert(RBNode root, RBNode node) {
        if (root == null) {
            return node;
        }
        if (node.key < root.key) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else if (node.key > root.key) {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }
        return root;
    }
    
    // Fix-up om de Red-Black eigenschappen te herstellen
    private void fixInsertion(RBNode z) {
        while (z.parent != null && z.parent.color == Color.RED) {
            RBNode grandparent = z.parent.parent;
            if (grandparent != null && z.parent == grandparent.left) {
                RBNode uncle = grandparent.right;
                // Case 1: Oom is rood
                if (uncle != null && uncle.color == Color.RED) {
                    grandparent.color = Color.RED;
                    z.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    z = grandparent;
                } else {
                    // Case 2: z is rechtskind
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }
                    // Case 3: z is linkskind
                    z.parent.color = Color.BLACK;
                    grandparent.color = Color.RED;
                    rotateRight(grandparent);
                }
            } else if (grandparent != null) {
                RBNode uncle = grandparent.left;
                // Symmetrische gevallen voor de andere kant
                if (uncle != null && uncle.color == Color.RED) {
                    grandparent.color = Color.RED;
                    z.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    z = grandparent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = Color.BLACK;
                    grandparent.color = Color.RED;
                    rotateLeft(grandparent);
                }
            }
        }
        // Wortel is altijd zwart
        if (root.color == Color.RED) {
            root.color = Color.BLACK;
        }
    }
    
    private void rotateLeft(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }
    
    private void rotateRight(RBNode y) {
        RBNode x = y.left;
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }
}

public class TreeDemo {
    public static void main(String[] args) {
        // Voorbeeld AVL
        AVLTree avl = new AVLTree();
        avl.insert(10);
        avl.insert(20);
        avl.insert(5);
        avl.insert(4);
        avl.insert(15);
        // Na deze invoegingen is de AVL-boom opnieuw in balans gebracht na elke insert.
        
        // Voorbeeld Red-Black
        RedBlackTree rbt = new RedBlackTree();
        rbt.insert(10);
        rbt.insert(20);
        rbt.insert(5);
        rbt.insert(4);
        rbt.insert(15);
        // Na deze invoegingen heeft de Red-Black Tree fixInsertion gebruikt om de boom in balans te houden.
    }
}
