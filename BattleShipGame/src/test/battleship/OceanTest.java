package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OceanTest {

    Ocean ocean;

    @BeforeEach
    void setUp() {
        ocean = new Ocean();
    }

    @AfterEach
    void tearDown() {
        ocean = null;
    }

    /**
     * checks if number of hits is equal to 0 in the beginning of the game
     */
    @Test
    void getHitCount() {
        assertEquals(0,  new Ocean().getHitCount());
    }

    /**
     * checks if number of sunk ships is equal to 0 in the beginning of the game
     */
    @Test
    void getShipsSunk() {
        assertEquals(0, ocean.getShipsSunk());
    }

    /**
     * checks if method isGameOver equals to false at the beginning of the game
     */
    @Test
    void isGameOver() {
        assertFalse(ocean.isGameOver());
    }
}