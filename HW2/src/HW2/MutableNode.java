package HW2;

import java.util.Collection;

public class MutableNode<T extends Number> implements Node {

    private T value;
    private MutableNode<T> parent;
    private Collection<Node<T>> children;

    public MutableNode(T value, MutableNode<T> parent, Collection<Node<T>> children) {
        this.value = value;
        this.parent = parent;
        this.children = children;
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
        if (indents < 0)
            indents = 0;
        System.out.println(' ' * indents + value.toString());
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public Number getValue() {
        return value;
    }

    public void setParent(MutableNode<T> parent) {
        this.parent = parent;
    }

    public void setChildren(Collection<MutableNode<T>> children) {
        this.children.clear();
        for (Node<T> child : children) {
            this.children.add(child);
        }
    }

    public void addChild(MutableNode<T> child) {
        children.add(child);
    }

    public void removeChild(MutableNode<T> child) {

        if(!child.getChildren().isEmpty()) {
            for (Node<T> everyChild : child.getChildren()) {
                child.removeChild((MutableNode<T>) everyChild);
            }
        }
        child = null;
    }
}
