/*
 * Class Commentator
 *
 * Version 1.0
 *
 * Ivanov Daniil BCS182 HSE. 2020
 */

package diceGame;

import java.util.Comparator;
import java.util.List;

class Commentator extends Thread {

    /**
     * Information which commentator announces
     */
    public static String info;

    /**
     * Constructor
     */
    Commentator() {
        info = "";
    }

    /**
     * Sets {@link #info}
     *
     * @param s - {@link #info}
     */
    static void setInfo(String s) {
        info = s;
    }

    /**
     * Prints info if it's not empty until commentator will be stopped as a thread
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (info) {
                if (!info.isEmpty()) {
                    System.out.println(info);
                    info = "";
                    try {
                        info.notify();
                    } catch (IllegalMonitorStateException ignored) {
                    }
                }
            }
        }
    }

    /**
     * Prints final scoreboard.
     */
    static void printFinalResults() {
        List<Player> players = Game.getPlayers();
        players.sort(Comparator.comparingInt(Player::getNumberOfWonRounds).reversed());
        for (int i = 0; i < Game.getNumberOfPlayers(); ++i)
            System.out.println(String.format("%d. Player №%d - %d won rounds",
                    i + 1, players.get(i).getIdentificator(), players.get(i).getNumberOfWonRounds()));
    }
}
