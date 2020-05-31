package battleship;

/**
 * Subclass of ship, releasing empty space in the field
 */
public class EmptySea extends Ship {
    /**
     * Constructor of empty space in the field, referring to constructor of base class
     */
    public EmptySea() {
        super(0,0,true);
        length = 1;
    }

    /**
     * This method overrides shootAt(int row, int column) that is inherited from Ship,
     * and always returns false to indicate that nothing was hit.
     * @param row of the field where we shoot
     * @param column of the field where we shoot
     * @return always false as nothing was hit
     */
    @Override
    public boolean shootAt(int row, int column) {
        hit[0] = true;
        return false;
    }

    /**
     * Checks if empty space was shot
     * @param row where we want to check
     * @param column where we want to check
     * @return true if user ever shot at this empty space, false in opposite
     */
    @Override
    public boolean isShot(int row, int column) {
        return hit[0];
    }

    /**
     * This is empty space, it's not the ship, so it can't be sunk
     * @return always  false
     */
    @Override
    public boolean isSunk() {
        return false;
    }

    /**
     * return condition of empty cell in field
     * @return '-' if this empty place was ever shot, else '.'
     */
    @Override
    public String toString() {
        return isShot(getBowRow(), getBowColumn()) ? "-" : ".";
    }
}
