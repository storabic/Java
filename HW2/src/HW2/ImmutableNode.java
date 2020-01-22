package HW2;

import java.util.Collection;

public class ImmutableNode<T extends Number> implements Node {

    /**
     * value stored in node
     */
    T value;
    /**
     * parent of this node
     */
    ImmutableNode<T> parent;
    /**
     * Node's children
     */
    Collection<Node<T>> children;

    /**
     * Constructor
     * @param value
     * @param parent
     * @param children
     */
    public ImmutableNode(T value, ImmutableNode<T> parent, Collection<ImmutableNode<T>> children){
        this.value = value;
        this.parent = parent;
        for (Node<T> child : children) {
            this.children.add(child);
        }
    }


    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return children;
    }

    /**
     * prints node
     * @param indents
     */
    @Override
    public void print(int indents) {
        for (int i = 0; i < indents; i++)
            System.out.print(' ');
        System.out.println(value);
    }

    @Override
    public Number getValue() {
        return value;
    }
}
