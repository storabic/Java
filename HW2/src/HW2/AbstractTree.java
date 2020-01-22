package HW2;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public abstract class AbstractTree<T extends Number> {

    /**
     * Tree root
     */
    protected Node<T> root;

    /**
     * Operator of addition
     */
    protected BinaryOperator<T> adder;

    /**
     * Sum of values in tree nodes
     */
    protected T sum;

    /**
     * Comparator of two nodes values
     */
    protected Comparator<T> comparator;

    /**
     * Neutral addition element
     */
    protected T zero;


    public Node<T> getRoot() {
        return root;
    }

    public int getSize() {
        return dfsSize(getRoot());
    }

    /**
     * support recursive method, walks all the tree to count its size
     * @param currentNode
     * @return
     */
    private int dfsSize(Node<T> currentNode) {
        int size = 1;
        for (Node<T> child : currentNode.getChildren()) {
            size += dfsSize(child);
        }
        return size;
    }

    /**
     * support recursive method, walks all the tree to count sum of nodes values
     * @param root
     */
    private void dfsSum(Node<T> root) {
        sum = adder.apply(sum, root.getValue());
        for (Node<T> child : root.getChildren()) {
            dfsSum(child);
        }
    }

    public T getSum() {
        sum = zero;
        dfsSum(getRoot());
        return sum;
    }

    /**
     * removes subtree with given root
     * @param rootSubTree root
     * @return
     */
    public abstract AbstractTree<T> removeSubtree(Node<T> rootSubTree);
    public abstract AbstractTree<T> maximize(int k);
    public abstract AbstractTree<T> maximize();
}