package diceGame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentatorTest {

    private Commentator commentator;

    @BeforeEach
    void setUp() {
        commentator = new Commentator();
    }

    @AfterEach
    void tearDown() {
        commentator = null;
    }

    @Test
    void setInfo() {
        Commentator.setInfo("TestString");
        Field field = null;
        try {
            field = commentator.getClass().getDeclaredField("info");
        } catch (NoSuchFieldException e) {
            assert (false);
        }
        field.setAccessible(true);
        try {
            assertEquals("TestString", field.get(commentator));
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void printFinalResults() {
        Field field = null;
        List<Player> players = null;
        try {
            field = Game.class.getDeclaredField("players");
        } catch (NoSuchFieldException e) {
            assert (false);
        }
        field.setAccessible(true);
        try {
            players = new ArrayList<Player>();
            for (int i = 1; i <= 3; ++i) {
                players.add(new Player(i + 1));
            }
            for (int i = 1; i <= 3; ++i) {
                for (int j = 1; j <= i; ++j)
                    players.get(i - 1).addWin();
            }
            field.set(Game.class, players);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        Commentator.printFinalResults();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        String content = buffer.toString();
        buffer.reset();
        players.sort(Comparator.comparingInt(Player::getNumberOfWonRounds).reversed());
        StringBuilder needed = new StringBuilder();
        for (int i = 0; i < Game.getNumberOfPlayers(); ++i)
            needed.append(String.format("%d. Player №%d - %d won rounds\r\n",
                    i + 1, players.get(i).getIdentificator(), players.get(i).getNumberOfWonRounds()));
        assertEquals(needed.toString(), content);
    }

    @Test
    void run() {
        commentator.start();
        /* Making this process do some hard work before interrupting commentator*/
        for (int i = 0; i < 1000000; ++i) {
            i = i;
        }
        commentator.interrupt();
        assertTrue(commentator.isInterrupted());
    }
}