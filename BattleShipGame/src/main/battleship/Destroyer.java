package battleship;

/**
 * Subclass of ship, releasing 2-cell ship
 */
public class Destroyer extends Ship {

    /**
     * Constructor of destroyer ship, referring to constructor of base class
     */
    public Destroyer() {
        super(0,0,true);
        length = 2;
    }

    /**
     * override getter of length
     * @return length of the ship
     */
    @Override
    public int getLength() {
        return length;
    }

    /**
     * returns type of the ship
     * @return type(string) "destroyer"
     */
    @Override
    public String getShipType() {
        return "destroyer";
    }

    /**
     * return condition of part of the ship
     * @return 'x' if ship is sunk, 'S' if this part of ship is hit, but ship isn't sunk
     */
    @Override
    public String toString() {
        return this.isSunk() ? "x" : "S";
    }
}
