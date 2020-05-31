package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubmarineTest {

    private Ship bs;

    @BeforeEach
    void setUp() {
        bs = new Submarine();
    }

    @AfterEach
    void tearDown() {
        bs = null;
    }

    @Test
    void getLength() {
        assertEquals(1, bs.getLength());
    }

    @Test
    void getShipType() {
        assertEquals("submarine", bs.getShipType());
    }

    @Test
    void testToString() {
        assertEquals("S", bs.toString());
    }
}