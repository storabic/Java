package battleship;

/**
 * Subclass of ship, releasing 4-cell ship
 */
public class Battleship extends Ship {

    /**
     * Constructor of main.battleship, referring to constructor of base class
     */
    public Battleship() {
        super(0,0,true);
        length = 4;
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
     * @return type(string) "main.battleship"
     */
    @Override
    public String getShipType() {
        return "battleship";
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
