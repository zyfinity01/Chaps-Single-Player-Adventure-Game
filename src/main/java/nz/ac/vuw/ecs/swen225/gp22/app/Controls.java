package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

/**
 * Handles key presses and initialising corresponding actions.
 */
public class Controls implements KeyListener {

  /**
   * Stores keys being pressed at a given time.
   */
  private Set<Integer> pressedKeys;

  /**
   * Actions to perform on key bind.
   */
  private Actions actions;

  /**
   * Recorder saves all moves to replay.
   */
  private Recorder recorder;

  /**
   * Initilise the controller.
   *
   * @param actions executed on specific key presses.
   */
  Controls(Actions actions) {
    this.recorder = new Recorder();
    this.actions = actions;
    pressedKeys = new HashSet<>();;
  }
  
  /**
   * Processes each key press state and fires off actions.
   */
  public void handle() {
    if (pressedKeys.isEmpty()) {
      return;
    }

    for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext();) {
      switch (it.next()) {
        case KeyEvent.VK_UP: // ⬆️
          actions.moveUp();
          break;
        case KeyEvent.VK_DOWN: // ⬇️
          actions.moveDown();
          break;
        case KeyEvent.VK_LEFT: // ⬅️
          actions.moveLeft();
          break;
        case KeyEvent.VK_RIGHT: // ➡️
          actions.moveRight();
          break;
        case KeyEvent.VK_SPACE: // Space bar
          actions.pause();
          break;
        case KeyEvent.VK_ESCAPE: // Esc
          actions.closeDialog();
          break;
        case KeyEvent.VK_CONTROL:
        case KeyEvent.VK_X: // CTRL-X
          actions.exit();
          break;
        case KeyEvent.VK_S: // CTRL-S
          actions.saveAndExit();
          break;
        case KeyEvent.VK_R: // CTRL-R
          actions.getGameAndResume();
          break;
        case KeyEvent.VK_1: // CTRL-1
          actions.startLevel1();
          break;
        case KeyEvent.VK_2: // CTRL-2
          actions.startLevel2();
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent event) {
    pressedKeys.add(event.getKeyCode());
    this.recorder.saveMovement(event.getKeyCode());
    handle();
  }

  @Override
  public void keyReleased(KeyEvent event) {
    pressedKeys.remove(event.getKeyCode());
  }

  @Override
  public void keyTyped(KeyEvent event) {
    /* not used */
  }
}
