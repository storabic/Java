package HW2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MutableNodeTest {

    MutableNode<Integer> parent;
    MutableNode<Integer> mutableNode;
    @BeforeAll
    void setUp() {
        mutableNode = new MutableNode<Integer>(5, null, new ArrayList<Node<Integer>>());
    }
    @Test
    void getParent() {
        assertEquals(null, mutableNode.getParent());
    }

    @Test
    void getValue() {
        assertEquals(5, mutableNode.getValue());
    }
}