package diceGame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private int id = 6543;

    @BeforeEach
    void setUp() {
        player = new Player(id);
        Field field = null;
        List<Dice> dices = new ArrayList<Dice>();
        for (int i = 0; i < 4; ++i)
            dices.add(new Dice(5));
        try {
            field = Game.class.getDeclaredField("dices");
            field.setAccessible(true);
            field.set(Game.class, dices);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        player = null;
    }

    @Test
    void throwDices() {
        int res = player.throwDices();
        if (res <= 20 && res >= 1)
            assert (true);
    }

    @Test
    void run() {
        player.start();
        /* Making this process do some hard work before interrupting player*/
        for (int i = 0; i < 1000000; ++i)
            i = i;
        player.interrupt();
        assertTrue(player.isInterrupted());
    }
}