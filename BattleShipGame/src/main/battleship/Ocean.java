package battleship;

import java.util.Random;

/**
 * Class, releasing field for main.battleship game
 */
public class Ocean {
    private Ship[][] ships = new Ship[10][10];
    private int shotsFired;
    private int hitCount;
    private int shipsSunk = 0;

    /**
     * getter of shotsFired
     * @return how many shots user did
     */
    public int getShotsFired() {
        return shotsFired;
    }

    /**
     * getter of hitCount
     * @return how many hits user did
     */
    public int getHitCount() {
        return hitCount;
    }

    /**
     * getter of shipsSunk
     * @return how many ships did user sunk
     */
    public int getShipsSunk() {
        return shipsSunk;
    }

    /**
     * Checks if game is over(all ships should be sunk)
     * @return true if game is over, else false
     */
    public boolean isGameOver() {
        return this.getShipsSunk() == 10;
    }

    /**
     * Constructor of Ocean class, sets all field as empty spaces
     */
    public Ocean() {
        for(int i = 0; i < 10; i++) {
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
        Ship[] randomShips = new Ship[] {new Battleship(), new Cruiser(), new Cruiser(), new Destroyer(),
                new Destroyer(), new Destroyer(),  new Submarine(), new Submarine(), new Submarine(), new Submarine() };
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
     * @param row where we check
     * @param column where we check
     * @return true if there is a ship at given position, else false
     */
    public boolean isOccupied(int row, int column) {
        return !(ships[row][column] instanceof EmptySea);
    }

    /**
     * Shoots at given position of the field
     * @param row where we shoot
     * @param column where we shoot
     * @return true if we hit not sunk ship, else false
     */
    public boolean shootAt(int row, int column) {
        shotsFired++;
        boolean wasSunk = false;
        if (ships[row][column].isSunk())
            wasSunk = true;
        ships[row][column].shootAt(row, column);
        if (!this.isOccupied(row, column) || wasSunk)
            return false;
        if (ships[row][column].isSunk()) {
            shipsSunk++;
            System.out.println("You just sank a " + ships[row][column].getShipType());
        }
        hitCount++;
        return true;
    }

    /**
     * Getter of ships
     * @return condition of the field, all its ships, hit cells and etc.
     */
    public Ship[][] getShipArray() {
        return ships;
    }

    /**
     * Prints condition of the field in console, all its ships, hit cells and etc.
     */
    public void print() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 10; j++) {
                if (ships[i][j].isShot(i, j) || ships[i][j] instanceof EmptySea) {
                    System.out.print(ships[i][j].toString() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}