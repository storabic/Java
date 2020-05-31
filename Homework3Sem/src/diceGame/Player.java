/**
 * Class Player
 * <p>
 * Version 1.0
 * <p>
 * Ivanov Daniil BCS182 HSE. 2020
 */

package diceGame;

class Player extends Thread {

    /**
     * Player id
     */
    private final int identificator;
    /**
     * Number of won rounds by this player
     */
    private int numberOfWonRounds;
    /**
     * Has rolled in this round
     */
    private boolean hasRolled;

    /**
     * @return {@link #identificator}
     */
    int getIdentificator() {
        return identificator;
    }

    /**
     * @return {@link #numberOfWonRounds}
     */
    int getNumberOfWonRounds() {
        return numberOfWonRounds;
    }

    /**
     * Resets {@link #hasRolled}
     */
    void resetHasRolled() {
        hasRolled = false;
    }

    /**
     * Constructor
     *
     * @param id - {@link #identificator}
     */
    Player(int id) {
        hasRolled = false;
        identificator = id;
        numberOfWonRounds = 0;
    }

    /**
     * Sends result of {@link #throwDices()} to round log
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!hasRolled && Log.isEmpty()) {
                synchronized (Log.getInstance()) {
                    hasRolled = true;
                    Log.generate(this, throwDices());
                }
            }
        }
    }

    /**
     * Increments {@link #numberOfWonRounds}
     */
    void addWin() {
        ++numberOfWonRounds;
    }

    /**
     * @return Sum of {@link Dice#roll()} for every dice
     */
    int throwDices() {
        int sum = 0;
        for (Dice dice : Game.getDices()) {
            sum += dice.roll();
        }
        return sum;
    }
}
