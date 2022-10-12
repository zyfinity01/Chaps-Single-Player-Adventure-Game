package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp22.app.App;

/**
 * @author Lawrence Schwabe, 300570719.
 * 
 * 
 */
public class FuzzTest {
  static final int numMoves = 1000; // change for however many tests needed
  /**
   * App context.
   * This should be reset on each test
   */
  private App app;

  /**
   * Run before each test.
   * Creates a new App context
   */
  @BeforeEach
  void setUp() {
    app = new App(false);
  }

  /**
   * Simulate a keypress
   * 
   * @param keyCode
   */
  public void pressKey(int keyCode) {
    var listener = app.getKeyListeners()[0];
    var key = new KeyEvent(app, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);

    listener.keyPressed(key);
    listener.keyReleased(key);
  }

  /**
   * Generate random keyPresses.
   * 
   */
  public static ArrayList<Integer> GenerateRandomMoves() {
    final int min = 1;
    final int max = 4;

    final Random random = new Random();

    ArrayList<Integer> keyCodes = new ArrayList<Integer>();
    HashMap<Integer, Integer> keyMap = new HashMap<Integer, Integer>(); // Int to Int, <K, V> where K is a counter and V
                                                                        // is keyCode

    keyMap.put(1, KeyEvent.VK_UP);
    keyMap.put(2, KeyEvent.VK_DOWN);
    keyMap.put(3, KeyEvent.VK_LEFT);
    keyMap.put(4, KeyEvent.VK_RIGHT);

    for (int i = 0; i < numMoves; i++) {

      int rand = random.nextInt(max - min + 1) + min;
      if (keyMap.keySet().contains(rand)) {
        keyCodes.add(keyMap.get(rand));

      }

    }
    System.out.println(keyCodes);
    return keyCodes;
  }

  /**
   * Runs the keyPress method on each element in supplied list.
   * 
   * @param randList
   */
  public void randomTests(ArrayList<Integer> randList) {

    // tests for 60s.
    long sysTime = System.currentTimeMillis();
    long end = sysTime + 60000;
    while (System.currentTimeMillis() < end) {

      for (Integer i : randList) {
        try {
          pressKey(i);

        } catch (IllegalStateException e) {
          System.out.println("Invalid input - check the input for keyCode: " + i + " + " + e.getStackTrace());
        }

      }

    }

  }

  /**
   * starts a new level 1 and creates random tests to run.
   * 
   */
  @Test
  public void test1() {

    // start level
    app.startLevel(1);
    // run tests
    randomTests(GenerateRandomMoves());
  }

  /**
   * starts a new level 2 and creates random tests to run.
   * 
   */
  @Test
  public void test2() {
    // TODO: implement level 2 when available.
    // start level
    app.startLevel(1);
    // run tests
    randomTests(GenerateRandomMoves());
  }

  /**
   * Testing for random number generator and map intialization.
   * 
   */
  @Test
  public void testMapGen() {

    ArrayList<Integer> test = FuzzTest.GenerateRandomMoves();

    assertTrue(test.size() == numMoves);

  }

}