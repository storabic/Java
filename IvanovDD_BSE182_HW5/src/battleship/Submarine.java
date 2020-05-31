package battleship;

/**
 * Subclass of ship, releasing 1-cell ship
 */
public class Submarine extends Ship {

    /**
     * Constructor of submarine ship, referring to constructor of base class
     */
    public Submarine() {
        super(0,0,true);
        length = 1;
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
     * @return type(string) "submarine"
     */
    @Override
    public String getShipType() {
        return "submarine";
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
