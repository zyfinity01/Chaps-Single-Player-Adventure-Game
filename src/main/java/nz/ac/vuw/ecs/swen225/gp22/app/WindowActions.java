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
   *
   * @param direction movement direction
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
  void save();

  /**
   * Remove game save for level.
   *
   * @param level level to clear
   */
  void clear(int level);

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
   * Replay a level.
   *
   * @param level level to replay
   */
  void replayLevel(int level);

  /**
   * Toggle pause state.
   */
  void togglePause();

  /**
   * User to select replay speed.
   */
  void setReplaySpeed();

  /**
   * Skip to next move in replay.
   */
  void stepReplay();

  /**
   * Return to main menu.
   */
  void toMainMenu();

  /**
   * Show message to user.
   *
   * @param message message to display
   */
  void showPopup(String message);
}
