package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OceanTest {

    private Ocean ocean;

    @BeforeEach
    void setUp() {
        ocean = new Ocean();
    }

    @AfterEach
    void tearDown() {
        ocean = null;
    }

    /**
     * checks if number of sunk ships is equal to 0 in the beginning of the game
     */
    @Test
    void getShipsSunk() {
        assertEquals(0, ocean.getSunkCount());
    }

    /**
     * checks if method isGameOver equals to false at the beginning of the game
     */
    @Test
    void isGameOver() {
        assertFalse(ocean.isGameOver());
    }

    @Test
    void testPlaceAllShipsRandomly() {
        try {
            ocean.placeAllShipsRandomly();
        } catch (Exception e) {
            assert (false);
        }
        assert (true);
    }

    @Test
    void testShootAt() {
        ocean.placeAllShipsRandomly();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (ocean.shootAt(i, j)) {
                    assert(true);
                    return;
                }
            }
        }
        assert(false);
    }

    @Test
    void testGetWholeCount() {
        assertEquals(10, ocean.getWholeCount());
        ocean.placeAllShipsRandomly();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                ocean.shootAt(i, j);
            }
        }
        assertEquals(0, ocean.getWholeCount());
    }


    @Test
    void testGetShip() {
        ocean.placeAllShipsRandomly();
        if (ocean.getShip(1, 1) instanceof Ship) {
            assert(true);
            return;
        }
        assert(false);
    }

    @Test
    void testGetShotsFired() {
        assertEquals(0, ocean.getShotsFired());
        ocean.placeAllShipsRandomly();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                ocean.shootAt(i, j);
            }
        }
        assertEquals(100, ocean.getShotsFired());
    }

    @Test
    void testIsSunk() {
        ocean.placeAllShipsRandomly();
        assertFalse(ocean.isSunk(1, 1));
    }
}