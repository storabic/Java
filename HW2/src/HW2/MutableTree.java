package HW2;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BinaryOperator;

public class MutableTree<T extends Number> extends AbstractTree<T> {

    /**
     * Constructor
     * @param root
     * @param adder
     * @param zero
     * @param comparator
     */
    public MutableTree(Node<T> root, BinaryOperator<T> adder, T zero, Comparator<T> comparator) {
        this.root = root;
        this.adder = adder;
        this.zero = zero;
        this.comparator = comparator;
    }

    /**
     * removes subtree with the given root
     * @param rootSubTree root
     * @return
     */
    @Override
    public AbstractTree<T> removeSubtree(Node<T> rootSubTree) {
        try {
            rootSubTree.getParent().getChildren().remove(rootSubTree);
        }
        catch(Exception e) {}
        rootSubTree = null;
        return this;
    }


    @Override
    public AbstractTree<T> maximize(int k) {
        return null;
    }


    public AbstractTree<T> maximize() {
        return null;
    }

}