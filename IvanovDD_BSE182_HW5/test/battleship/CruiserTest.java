package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CruiserTest {

    private Cruiser bs;

    @BeforeEach
    void setUp() {
        bs = new Cruiser();
    }

    @AfterEach
    void tearDown() {
        bs = null;
    }

    @Test
    void getLength() {
        assertEquals(3, bs.getLength());
    }

    @Test
    void getShipType() {
        assertEquals("cruiser", bs.getShipType());
    }

    @Test
    void testToString() {
        assertEquals("S", bs.toString());
    }
}