package HW2;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class ImmutableTree<T extends Number> extends AbstractTree<T> {

    /**
     * Constructor
     * @param root
     * @param adder
     * @param zero
     * @param comparator
     */
    public ImmutableTree(Node<T> root, BinaryOperator<T> adder, T zero, Comparator<T> comparator) {
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
        MutableTree<T> newTree= (MutableTree<T>)(AbstractTree<T>)this;
        newTree.removeSubtree(rootSubTree);
        return newTree;
    }

    @Override
    public AbstractTree<T> maximize(int k) {
        return null;
    }

    @Override
    public AbstractTree<T> maximize() {
        return null;
    }
}
