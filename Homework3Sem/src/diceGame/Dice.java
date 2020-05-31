/*
 * Class Dice
 *
 * Version 1.0
 *
 * Ivanov Daniil BCS182 HSE. 2020
 */
package diceGame;

import java.util.Random;

/**
 * Class imitating real dice
 */
class Dice {
    /**
     * number of dice sides
     */
    private final int numberOfSides;

    /**
     * Constructor
     * @param num stands for {@link #numberOfSides}
     */
    Dice(int num) {
        numberOfSides = num;
    }

    /**
     * Imitates real dice roll, using {@link Random} methods
     * @return Rolled points
     */
    int roll() {
        Random rnd = new Random();
        return 1 + rnd.nextInt(numberOfSides);
    }
}