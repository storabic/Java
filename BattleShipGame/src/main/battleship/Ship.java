package battleship;

/**
 * @version 1.0
 */
public class Ship {
    protected int bowRow;
    protected int bowColumn;
    protected int length;
    protected boolean horizontal;
    protected boolean [] hit = new boolean[4];

    /**
     * getter of length
     * @return just random number(1 in this case)
     */
    public int getLength() {
        return 1;
    }

    /**
     * getter of bowRow
     * @return row where lies head of the ship
     */
    public int getBowRow() {
        return bowRow;
    }

    /**
     * getter of bowColumn
     * @return column where lies head of the ship
     */
    public int getBowColumn() {
        return bowColumn;
    }

    /**
     *getter of Horizontal
     * @return if ship is horizontal
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Additional method
     * Constructor of Ship class
     * @param row of ship's head
     * @param column of ship's head
     * @param horizontal positioning of ship
     */
    public Ship(int row, int column, boolean horizontal) {
        setBowRow(row);
        setBowColumn(column);
        setHorizontal(horizontal);
   }

    /**
     * Additional method(not from the specialization)
     * checks if part of the ship was hit
     * @param row where we want to check
     * @param column where we want to check
     * @return is this part of the ship is hit
     * Principe: (row - this.getBowRow()) + (column - this.getBowColumn()) works because
     * one of this for part of the ship will be > 0 and another is 0, but if
     * this is not part of the ship we won't get value of hit of this index so we get exception and then false
     */
    public boolean isShot(int row, int column) {
        try {
            return hit[row - this.getBowRow() + column - this.getBowColumn()];
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * setter of bowRow
     * @param row of ships's head we want to set
     */
    public void setBowRow(int row) {
        bowRow = row;
    }

    /**
     * setter of bowColumn
     * @param column of ship's head we want to set
     */
    public void setBowColumn(int column) {
        bowColumn = column;
    }

    /**
     * setter of horizontal
     * @param horizontal positioning of ship
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * in idea should return type of ship(submarine, cruiser, etc.)
     * @return empty string(this method is just to override it in subclasses
     */
    public String getShipType() {
        return "";
    }

    /**
     * checks if we can place the ship in this position of field
     * @param row of the field where we want to place the ship
     * @param column of the field where we want to place the ship
     * @param horizontal positioning of the ship
     * @param ocean where we want to place the ship
     * @return true if we can place the ship at the given position, else false
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        Ship[][] field = ocean.getShipArray();
        int copyRow = row, copyColumn = column;
        for (int i = 0; i < this.getLength(); i++) {
            if(horizontal)
                copyColumn = column + i;
            else
                copyRow = row + i;
            if (copyColumn > 9 || copyColumn <  0 || copyRow > 9 || copyRow < 0)
                return false;
            for (int x = -1; x <= 1; x++)
                for (int y = -1; y <= 1; y++) {
                    try {
                        if (!(field[copyRow + x][copyColumn + y] instanceof EmptySea))
                            return false;
                    } catch(Exception e) {
                        continue;
                    }
                }
        }
        return true;
    }

    /**
     * places the ship at given position of field
     * @param row where we set
     * @param column where we set
     * @param horizontal positioning of the ship
     * @param ocean - field where we set the ship
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        this.setBowRow(row);
        this.setBowColumn(column);
        this.setHorizontal(horizontal);
        Ship[][] field = ocean.getShipArray();
        for (int i = 0; i < this.getLength(); i++) {
            if (horizontal) {
                field[row][column + i] = this;
            } else {
                field[row + i][column] = this;
            }
        }
    }

    /**
     * If a part of the ship occupies the given row and column,
     * and the ship hasn't been sunk, mark that part of the ship as "hit"
     * (in the hit array, 0 indicates the bow) and return true, otherwise return false.
     * @param row of the field where we shoot
     * @param column of the field where we shoot
     * @return true if we hit the real ship, else false
     */
    public boolean shootAt(int row, int column) {
        if(this.isSunk())
            return false;
        if (this.isHorizontal()) {
            for (int j = 0; j < this.getLength(); j++)
                if (this.getBowColumn() + j == column) {
                    hit[j] = true;
                }
        } else {
            for (int i = 0; i < this.getLength(); i++)
                if (this.getBowRow() + i == row) {
                    hit[i] = true;
                }
        }
        return true;
    }

    /**
     * Check if all parts of the ship were hit, so if ship was sunk
     * @return true if every part of the ship has been hit, false otherwise.
     */
    public boolean isSunk() {
        for (int i = 0; i < this.getLength(); i++) {
            if (hit[i] == false)
                return false;
        }
        return true;
    }
}
