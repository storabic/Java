package HW2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MutableTreeTest {

    MutableTree<Integer> intTree;
    MutableNode<Integer> rightChild;

    @BeforeEach
    void setUp() {
        MutableNode<Integer> root = new MutableNode<Integer>(10, null, new LinkedList<Node<Integer>>());
        for (int i = 3; i <= 7; i += 2)
            root.addChild(new MutableNode<Integer>(i, root, new LinkedList<Node<Integer>>()));
        rightChild = new MutableNode<Integer>(1, root, new LinkedList<Node<Integer>>());
        root.addChild(rightChild);
        rightChild.addChild(new MutableNode<Integer>(4, rightChild, new LinkedList<Node<Integer>>()));
        rightChild.addChild(new MutableNode<Integer>(6, rightChild, new LinkedList<Node<Integer>>()));

        intTree = new MutableTree<Integer>(root, (x, y) -> x + y, 0, (i1, i2) -> i1 > i2 ? 1 : i1 < i2 ? -1 : 0);
    }

    @Test
    void getSum() {
       assertEquals(36, intTree.getSum());
    }

    @Test
    void getSize() {
        assertEquals(7, intTree.getSize());
    }


    @Test
    void removeSubtree() {
        intTree.removeSubtree(rightChild);
        assertEquals(36 - 1 - 4 - 6, intTree.getSum());
        assertEquals(7 - 3, intTree.getSize());
    }

    @AfterAll
    void tearDown() {
        intTree = null;
        rightChild = null;
    }
}