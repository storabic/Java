package battleship;

import java.util.Scanner;

/**
 * Class, releasing main.battleship game
 */
public class BattleshipGame {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Reads number, checks if it can be the number of row or column of main.battleship field
     *
     * @return number 0-9
     */
    private static int getBattleshipNumber() {
        try {
            String s = scanner.nextLine();
            int x = Integer.parseInt(s);
            if (x > 9 || x < 0)
                throw new Exception();
            return x;
        } catch (Exception e) {
            System.out.println("Enter 0-9 number");
            return getBattleshipNumber();
        }
    }

    /**
     * Entry point of program, interface for user.
     *
     * @param args launching program arguments
     */
    public static void main(String[] args) {
        do {
            Ocean ocean = new Ocean();
            ocean.placeAllShipsRandomly();
            System.out.println("Welcome to the Battleship game!");
            do {
                ocean.print();
                System.out.println("Enter 2 numbers in different lines, where to strike:");
                int a = getBattleshipNumber();
                int b = getBattleshipNumber();
                if (ocean.shootAt(a, b)) {
                    if (!ocean.getShipArray()[a][b].isSunk()) {
                        System.out.println("hit");
                    }
                } else {
                    System.out.println("miss");
                }
            } while (!ocean.isGameOver());
            ocean.print();
            System.out.println("The game is over\nHits: " + ocean.getHitCount() + " Shots: " + ocean.getShotsFired());
            System.out.println("Enter CONTINUE to play another game");
        } while (scanner.next().equals("CONTINUE"));
        System.out.println("Thank you for playing the game!");
    }
}
