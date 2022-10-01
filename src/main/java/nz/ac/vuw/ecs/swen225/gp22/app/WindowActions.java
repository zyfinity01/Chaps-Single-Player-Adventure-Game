package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;

/**
 * Actions the player can perform via the keyboard.
 *
 * @author Sam Redmond, 300443508
 */
interface WindowActions {
  /**
   * Move player in the upwards direction.
   */
  void move(Direction direction);

  /**
   * Pause game.
   */
  void pause();

  /**
   * Close open dialog box.
   */
  void unpause();

  /**
   * Exit game, current state will be lost but when opened again,
   * will start on the last unfinished level.s
   */
  void exit();

  /**
   * Save level state and close.
   */
  void saveAndExit();

  /**
   * Select an existing game and continue.
   */
  void getGameAndResume();

  /**
   * Play level.
   *
   * @param level level to play.
   */
  void startLevel(int level);

  /**
   * Toggle pause state.
   */
  void togglePause();
}
