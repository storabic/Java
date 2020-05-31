/**
 * Class Main
 *
 * Version 1.0
 *
 * Ivanov Daniil BCS182 HSE. 2020
 */

package diceGame;

class Main {

    /**
     * Launches new dice game with parameters from command line
     * @param args should contain 3 numbers :
     *             1. 2 <= N <= 6 - number of players;
     *             2. 2 <= K <= 5 - number of dices;
     *             3. 1 <= M <= 100 - number of rounds player must win to win the game
     */
    public static void main(String[] args) {
        int n, k, m;
        try {
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
            m = Integer.parseInt(args[2]);
            if ((n > 6 || n < 2) || (k > 5 || k < 2) || (m < 1 || m > 100))
                throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Wrong arguments. N, K, M are integer values.\n" +
                    "2 <= N <= 6, 2 <= K <= 5, 1 <= M <= 100");
            return;
        }
        Game game = new Game(n, k, m);
    }
}