package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;

/**
 * Handles key presses and initialising corresponding actions.
 *
 * @author Sam Redmond, 300443508
 */
public class KeyController implements KeyListener {

  /**
   * Actions to perform on key bind.
   */
  private WindowActions actions;

  /**
   * Recorder saves all moves to replay.
   */
  private Recorder recorder;

  /**
   * Initilise the controller.
   *
   * @param actions executed on specific key presses.
   */
  KeyController(WindowActions actions) {
    this.actions = actions;
  }

  /**
   * Set the recorder.
   *
   * @param recorder to save moves to.
   */
  public void setRecorder(Recorder recorder) {
    this.recorder = recorder;
  }
  
  /**
   * Processes each key press state and fires off actions.
   *
   * @param event key event from the keyboard.
   */
  public void handle(KeyEvent event) {
    switch (event.getKeyCode()) {
      // UP
      case KeyEvent.VK_UP -> actions.move(Direction.Up);
      // DOWN
      case KeyEvent.VK_DOWN -> actions.move(Direction.Down);
      // LEFT
      case KeyEvent.VK_LEFT -> actions.move(Direction.Left);
      // RIGHT
      case KeyEvent.VK_RIGHT -> actions.move(Direction.Right);
      // SPACE BAR
      case KeyEvent.VK_SPACE -> actions.pause();
      // ESC
      case KeyEvent.VK_ESCAPE -> actions.unpause();
      // CTRL-X
      case KeyEvent.VK_X -> {
        if (event.isControlDown()) {
          actions.exit();
        }
      }
      // CTRL-S
      case KeyEvent.VK_S -> {
        if (event.isControlDown()) {
          actions.saveAndExit();
        }
      }
      // CTRL-R
      case KeyEvent.VK_R -> {
        if (event.isControlDown()) {
          actions.getGameAndResume();
        }
      }
      // CTRL-1
      case KeyEvent.VK_1 -> {
        if (event.isControlDown()) {
          actions.startLevel(1);
        }
      }
      // CTRL-2
      case KeyEvent.VK_2 -> {
        if (event.isControlDown()) {
          actions.startLevel(2);
        }
      }
      default -> { }
    }
  }

  @Override
  public void keyPressed(KeyEvent event) {
    handle(event);
    if (this.recorder != null) {
      this.recorder.savePlayerMovement(event.getKeyCode());
    }
  }

  @Override
  public void keyReleased(KeyEvent event) {
    /* not used */
  }

  @Override
  public void keyTyped(KeyEvent event) {
    /* not used */
  }
}
