package diceGame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTest {

    private Dice dice;
    @BeforeEach
    void setUp() {
        dice = new Dice(300);
    }

    @AfterEach
    void tearDown() {
        dice = null;
    }

    @Test
    void roll() {
        int res = dice.roll();
        if (res >= 0 && res <= 300)
            assert (true);
    }
}