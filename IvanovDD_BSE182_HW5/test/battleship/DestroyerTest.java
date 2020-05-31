package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DestroyerTest {

    private Destroyer bs;

    @BeforeEach
    void setUp() {
        bs = new Destroyer();
    }

    @AfterEach
    void tearDown() {
        bs = null;
    }

    @Test
    void getLength() {
        assertEquals(2, bs.getLength());
    }

    @Test
    void getShipType() {
        assertEquals("destroyer", bs.getShipType());
    }

    @Test
    void testToString() {
        assertEquals("S", bs.toString());
    }
}