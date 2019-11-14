package battleship;

/**
 * Subclass of ship, releasing 3-cell ship
 */
public class Cruiser extends Ship {

    /**
     * Constructor of cruiser ship, referring to constructor of base class
     */
    public Cruiser() {
        super(0,0,true);
        length = 3;
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
     * @return type(string) "cruiser"
     */
    @Override
    public String getShipType() {
        return "cruiser";
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
