package diceGame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    private Field leader;
    private Field bestResult;
    private Field numberOfRolls;
    private Field info;
    private Field players;

    @BeforeEach
    void setUp() {
        leader = null;
        bestResult = null;
        numberOfRolls = null;
        info = null;
        players = null;
        try {
            leader = Log.class.getDeclaredField("leader");
            bestResult = Log.class.getDeclaredField("bestResult");
            numberOfRolls = Log.class.getDeclaredField("numberOfRolls");
            info = Commentator.class.getDeclaredField("info");
            players = Game.class.getDeclaredField("players");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        leader.setAccessible(true);
        bestResult.setAccessible(true);
        numberOfRolls.setAccessible(true);
        info.setAccessible(true);
        players.setAccessible(true);
    }

    @AfterEach
    void tearDown() {
        try {
            leader.set(Log.class, null);
            bestResult.set(Log.class, 0);
            numberOfRolls.set(Log.class, 0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void generate1() {
        Field maxResult = null;
        try {
            maxResult = Game.class.getDeclaredField("maxResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        maxResult.setAccessible(true);
        try {
            maxResult.set(Game.class, 4);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Player testPlayer = new Player(3);
        List<Player> testPlayers = new ArrayList<Player>();
        testPlayers.add(testPlayer);
        try {
            players.set(Game.class, testPlayers);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.generate(testPlayer, 4);
        String needed = "Player №3 rolled 4.\n" +
                "Round winner is player №3 with 4. He won 1 round(s) already\n" +
                "Player №3 won the biggest number of round(s)(1)";
        try {
            assertEquals(needed, info.get(Commentator.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void generate2() {
        Field maxResult = null;
        try {
            maxResult = Game.class.getDeclaredField("maxResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        maxResult.setAccessible(true);
        try {
            maxResult.set(Game.class, 4);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Player testPlayer = new Player(3);
        List<Player> testPlayers = new ArrayList<Player>();
        testPlayers.add(testPlayer);
        try {
            players.set(Game.class, testPlayers);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.generate(testPlayer, 3);
        Field info = null;
        try {
            info = Commentator.class.getDeclaredField("info");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        info.setAccessible(true);
        String needed = "Player №3 rolled 3. Round leader is player №3 with 3.";
        try {
            assertEquals(needed, info.get(Commentator.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void generate3() {
        Field maxResult = null;
        try {
            maxResult = Game.class.getDeclaredField("maxResult");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        maxResult.setAccessible(true);
        try {
            maxResult.set(Game.class, 4);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field maxWins = null;
        try {
            maxWins = Game.class.getDeclaredField("maxNumberOfWins");
            maxWins.setAccessible(true);
            maxWins.set(Game.class, 4);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Player testPlayer = new Player(3);
        for (int i = 0; i < 3; ++i)
            testPlayer.addWin();
        List<Player> testPlayers = new ArrayList<Player>();
        testPlayers.add(testPlayer);

        Field commentator = null;
        try {
            commentator = Game.class.getDeclaredField("commentator");
            commentator.setAccessible(true);
            commentator.set(Game.class, new Commentator());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            players.set(Game.class, testPlayers);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.generate(testPlayer, 4);
        String needed = "Player №3 rolled 4.\n" +
                "Round winner is player №3 with 4. He won 4 round(s) already";
        try {
            assertEquals(needed, info.get(Commentator.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void clear() {
        try {
            leader.set(Log.class, new Player(3));
            bestResult.set(Log.class, 4);
            numberOfRolls.set(Log.class, 9);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.clear();

        try {
            assertNull(leader.get(Log.class));
            assertEquals(0, bestResult.get(Log.class));
            assertEquals(0, numberOfRolls.get(Log.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance() {
        Field inst = null;
        try {
            inst = Log.class.getDeclaredField("instance");
            inst.setAccessible(true);
            Log.getInstance();
            assertEquals(inst.get(Log.class), Log.getInstance());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}