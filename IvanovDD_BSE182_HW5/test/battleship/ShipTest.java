package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    private Ship ship;

    @BeforeEach
    void setUp() {
        ship = new Ship(3, 4, true);
    }

    @AfterEach
    void tearDown() {
        ship = null;
    }

    @Test
    void getLength() {
        assertEquals(1, ship.getLength());
    }

    @Test
    void getBowRow() {
        Field field = null;
        try {
            field = ship.getClass().getDeclaredField("bowRow");
        } catch (NoSuchFieldException e) {
            assert (false);
        }
        try {
            field.setAccessible(true);
            assertEquals(field.getInt(ship), ship.getBowRow());
        } catch (IllegalAccessException e) {
            assert (false);
        }
    }

    @Test
    void getBowColumn() {
        Field field = null;
        try {
            field = ship.getClass().getDeclaredField("bowColumn");
        } catch (NoSuchFieldException e) {
            assert (false);
        }
        try {
            field.setAccessible(true);
            assertEquals(field.getInt(ship), ship.getBowColumn());
        } catch (IllegalAccessException e) {
            assert (false);
        }
    }

    @Test
    void isHorizontal() {
        assertTrue(ship.isHorizontal());
        assertFalse(new Ship(0, 0, false).isHorizontal());
    }

    @Test
    void setBowRow() {
        Field field = null;
        try {
            field = ship.getClass().getDeclaredField("bowRow");
        } catch (NoSuchFieldException e) {
            assert (false);
        }
        int row = 7;
        ship.setBowRow(row);
        try {
            field.setAccessible(true);
            assertEquals(row, field.getInt(ship));
        } catch (IllegalAccessException e) {
            assert (false);
        }
    }

    @Test
    void setBowColumn() {
        Field field = null;
        try {
            field = ship.getClass().getDeclaredField("bowColumn");
        } catch (NoSuchFieldException e) {
            assert (false);
        }
        int column = 7;
        ship.setBowColumn(column);
        try {
            field.setAccessible(true);
            assertEquals(column, field.getInt(ship));
        } catch (IllegalAccessException e) {
            assert (false);
        }
    }

    @Test
    void getShipType() {
        assertEquals("", ship.getShipType());
    }

    @Test
    void isSunk() {
        assertFalse(ship.isSunk());
    }

    @Test
    void isShot() {
        assertFalse(ship.isShot(5, 7));
    }

    @Test
    void okToPlaceShipAt() {
        Ocean ocean = new Ocean();
        assertTrue(ship.okToPlaceShipAt(5, 7, true, ocean));
    }

    @Test
    void placeShipAt() {
        try {
            ship.placeShipAt(5, 7, true, new Ocean());
        } catch (Exception e) {
            assert (false);
        }
        assert (true);
    }

    @Test
    void shootAt() {
        assertTrue(ship.shootAt(3, 4));
        assertFalse(ship.shootAt(3, 4));
    }


    @Test
    void isHit() {
        assertFalse(ship.isHit());
        ship.shootAt(3, 4);
        assertTrue(ship.isHit());
    }
}