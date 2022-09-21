package test.nz.ac.vuw.ecs.swen225.gp22.fuzz;

import nz.ac.vuw.ecs.swen225.gp22.app.App;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Lawrence Schwabe, 300570719.
 * 
 * 
 */
public class FuzzTest {

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
    app = new App();
  }

  /**
   * Simulate a keypress
   * 
   * @param keyCode
   */
  public void pressKey(int keyCode) {
    var listener = app.getKeyListeners()[0];
    var key = new KeyEvent(app, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, 'Z');

    listener.keyPressed(key);
    listener.keyReleased(key);
  }

  @Test
  public void test1() {
    // move player up
    pressKey(KeyEvent.VK_UP);
  }

  @Test
  public void test2() {
    // move player right
    pressKey(KeyEvent.VK_RIGHT);
  }

}