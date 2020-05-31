/**
 * Class Log
 * <p>
 * Version 1.0
 * <p>
 * Ivanov Daniil BCS182 HSE. 2020
 */

package diceGame;

class Log {
    /**
     * Singleton-like self instance
     */
    private static Log instance;
    /**
     * Round leader
     */
    private static Player leader;
    /**
     * {@link #leader} result
     */
    private static int bestResult;
    /**
     * Number of rolls by now
     */
    private static int numberOfRolls;
    /**
     * Round log
     */
    private static String info;

    /**
     * Constructor
     */
    private Log() {
        clear();
        numberOfRolls = 0;
        info = "";
    }

    /**
     * Singleton-like {@link #instance} getter
     * @return {@link #instance}
     */
    static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    /**
     * @return {@code true} if {@link #info} is empty
     */
    static boolean isEmpty() {
        return info.isEmpty();
    }

    /**
     * Generates {@link #info}, following given rules.
     * Also controls round process.
     * @param player current player
     * @param result current player's result
     */
    static void generate(Player player, int result) {
        ++numberOfRolls;
        info = String.format("Player №%d rolled %d.", player.getIdentificator(), result);
        if (result > bestResult) {
            leader = player;
            bestResult = result;
        }
        if (result != Game.getMaxResult()) {
            info += String.format(" Round leader is player №%d with %d.", leader.getIdentificator(), bestResult);
        }

        if (numberOfRolls == Game.getNumberOfPlayers() || result == Game.getMaxResult()) {
            Game.beginNewRound();
            leader.addWin();
            info += String.format("\nRound winner is player №%d with %d. He won %d round(s) already",
                    leader.getIdentificator(), bestResult, leader.getNumberOfWonRounds());
            if (Game.isOver()) {
                synchronized (Commentator.info) {
                    Commentator.setInfo(info);
                    try {
                        Commentator.info.wait();
                    } catch (InterruptedException | IllegalMonitorStateException ignored1) {
                    }
                }
                Commentator.printFinalResults();
                Game.stop();
                return;
            } else {
                Player overallLeader = Game.getLeader();
                info += String.format("\nPlayer №%d won the biggest number of round(s)(%d)",
                        overallLeader.getIdentificator(), overallLeader.getNumberOfWonRounds());
                synchronized (Commentator.info) {
                    Commentator.setInfo(info);
                    try {
                        Commentator.info.wait();
                    } catch (InterruptedException | IllegalMonitorStateException ignored1) {
                    }
                }
            }
            clear();
            Game.beginNewRound();
        } else {
            synchronized (Commentator.info) {
                Commentator.setInfo(info);
                try {
                    Commentator.info.wait();
                } catch (InterruptedException | IllegalMonitorStateException ignored1) {
                }
            }
            eraseInfo();
        }
    }

    /**
     * Resets round log
     */
    static void clear() {
        eraseInfo();
        bestResult = 0;
        numberOfRolls = 0;
        leader = null;
    }

    /**
     * Clears {@link #info}
     */
    private static void eraseInfo() {
        info = "";
    }
}
