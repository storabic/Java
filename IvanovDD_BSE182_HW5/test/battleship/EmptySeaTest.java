package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptySeaTest {

    EmptySea empty;
    @BeforeEach
    void setUp() {
        empty = new EmptySea();
    }

    @AfterEach
    void tearDown() {
        empty = null;
    }

    @Test
    void shootAt() {
        assertFalse(empty.shootAt(0, 0));
    }

    @Test
    void isShot() {
        assertFalse(empty.isShot(0, 0));
        empty.shootAt(0, 0);
        assertTrue(empty.isShot(0, 0));
    }

    @Test
    void isSunk() {
        assertFalse(empty.isSunk());
    }

    @Test
    void testToString() {
        assertEquals(".", empty.toString());
    }
}