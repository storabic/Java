/*
 * Class Game
 *
 * Version 1.0
 *
 * Ivanov Daniil BCS182 HSE. 2020
 */

package diceGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Game {

    /**
     * @return {@link #maxResult}
     */
    static int getMaxResult() {
        return maxResult;
    }

    /**
     * @return {@link #numberOfPlayers}
     */
    static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * @return {@link #dices}
     */
    static List<Dice> getDices() {
        return dices;
    }

    /**
     *
     * @return {@link #players}
     */
    static List<Player> getPlayers() {
        return players;
    }

    /**
     * Number of sides on dices
     * Can be changed
     */
    private static final int diceSides = 6;

    /**
     * Max sum which can be get by throwing a dice
     */
    private static int maxResult;

    /**
     * Max number of won rounds till game stops
     */
    private static int maxNumberOfWins;

    /**
     * Number of players in the game
     */
    private static int numberOfPlayers;

    /**
     * {@link List} of players in the game
     */
    private static List<Player> players;

    /**
     * {@link List} of dices
     */
    private static List<Dice> dices;

    /**
     * Commentator telling information about game process
     */
    private static Commentator commentator;

    /**
     * Constructor, also begins new game and starts {@link #players} and {@link #commentator} as threads
     * @param n stands for {@link #numberOfPlayers}
     * @param k stands for the number of {@link #dices}
     * @param m stands for {@link #maxNumberOfWins}
     */
    Game(int n, int k, int m) {
        maxResult = k * diceSides;
        numberOfPlayers = n;
        maxNumberOfWins = m;
        players = new ArrayList<Player>();
        for (int i = 1; i <= n; ++i)
            players.add(new Player(i));
        dices = new ArrayList<Dice>();
        for (int i = 0; i < k; ++i)
            dices.add(new Dice(diceSides));
        commentator = new Commentator();
        Log.getInstance(); // Log is similar to something like singleton, so we make his instance
        commentator.start();
        for (Player player : players) {
            player.start();
        }
    }

    /**
     * @return {@link Player} with the biggest number of won rounds
     */
    static Player getLeader() {
        return Collections.max(players, Comparator.comparingInt(Player::getNumberOfWonRounds));
    }

    /**
     * Check if game should end
     * @return {@code true} if some player reached max number of won rounds to end the game
     */
    static boolean isOver() {
        return (getLeader().getNumberOfWonRounds() == maxNumberOfWins);
    }

    /**
     * For every player changes flag if player has rolled dices in current round
     */
    static void beginNewRound() {
        for (Player player : players) {
            player.resetHasRolled();
        }
    }

    /**
     * Stops running threads {@link #players} and {@link #commentator}
     */
    static void stop() {
        commentator.interrupt();
        for (Player player : players) {
            player.interrupt();
        }
    }
}