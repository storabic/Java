package battleship;

import java.util.Random;

/**
 * Class, releasing field for main.battleship game
 */
public class Ocean {
    private final Ship[][] ships = new Ship[10][10];
    private int shotsFired;
    private int shipsSunk = 0;
    private int wholeShips = 10;

    /**
     * getter of shotsFired
     *
     * @return how many shots user did
     */
    public int getShotsFired() {
        return shotsFired;
    }

    /**
     * Getter of shipsSunk
     *
     * @return how many ships did user sunk
     */
    public int getSunkCount() {
        return shipsSunk;
    }

    /**
     * Getter of ships
     *
     * @return condition of the field, all its ships, hit cells and etc.
     */
    Ship[][] getShipArray() {
        return ships;
    }

    /**
     * Check if ship in given row and column is sunk
     *
     * @param row horizontal coordinate
     * @param column vertical coordinate
     * @return true if ship in given row and column is sunk
     */
    public boolean isSunk(int row, int column) {
        return ships[row][column].isSunk();
    }

    /**
     * return the ship which lies in given row and column
     *
     * @param row horizontal coordinate
     * @param column vertical coordinate
     * @return Ship
     */
    public Ship getShip(int row, int column) {
        return ships[row][column];
    }

    /**
     * getter for wholeShips
     *
     * @return whole ships number
     */
    public int getWholeCount() {
        return wholeShips;
    }

    /**
     * Checks if game is over(all ships should be sunk)
     *
     * @return true if game is over, else false
     */
    public boolean isGameOver() {
        return this.getSunkCount() == 10;
    }

    /**
     * Constructor of Ocean class, sets all field as empty spaces
     */
    public Ocean() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ships[i][j] = new EmptySea();
            }
        }
    }

    /**
     * Places 10 ships from main.battleship game at the field
     */
    public void placeAllShipsRandomly() {
        Random random = new Random();
        Ship[] randomShips = new Ship[]{new Battleship(), new Cruiser(), new Cruiser(), new Destroyer(),
                new Destroyer(), new Destroyer(), new Submarine(), new Submarine(), new Submarine(), new Submarine()};
        for (int i = 0; i < 10; i++) {
            int bowColumn, bowRow;
            boolean isHorizontal;
            do {
                bowColumn = random.nextInt(9);
                bowRow = random.nextInt(9);
                isHorizontal = random.nextBoolean();
            } while (!randomShips[i].okToPlaceShipAt(bowRow, bowColumn, isHorizontal, this));
            randomShips[i].placeShipAt(bowRow, bowColumn, isHorizontal, this);
        }
    }

    /**
     * Checks if main.battleship lies at given position
     *
     * @param row    where we check
     * @param column where we check
     * @return true if there is a ship at given position, else false
     */
    private boolean isOccupied(int row, int column) {
        return !(ships[row][column] instanceof EmptySea);
    }

    /**
     * Shoots at given position of the field
     *
     * @param row    where we shoot
     * @param column where we shoot
     * @return true if we hit not sunk ship, else false
     */
    public boolean shootAt(int row, int column) {
        ++shotsFired;
        boolean wasSunk = ships[row][column].isSunk();
        boolean wasHit = ships[row][column].isHit();
        ships[row][column].shootAt(row, column);
        if (!this.isOccupied(row, column) || wasSunk)
            return false;
        if (ships[row][column].isSunk()) {
            ++shipsSunk;
        }
        if (!wasHit) {
            --wholeShips;
        }
        return true;
    }
}