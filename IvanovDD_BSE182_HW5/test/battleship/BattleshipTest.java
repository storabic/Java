package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipTest {

    private Battleship bs;

    @BeforeEach
    void setUp() {
        bs = new Battleship();
    }

    @AfterEach
    void tearDown() {
        bs = null;
    }

    @Test
    void getLength() {
        assertEquals(4, bs.getLength());
    }

    @Test
    void getShipType() {
        assertEquals("battleship", bs.getShipType());
    }

    @Test
    void testToString() {
        assertEquals("S", bs.toString());
    }
}