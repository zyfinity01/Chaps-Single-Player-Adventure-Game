package nz.ac.vuw.ecs.swen225.gp22.app;

/**
 * Actions the player can perform via the keyboard.
 */
interface Actions {
  /**
   * Move player in the upwards direction.
   */
  void moveUp();

  /**
   * Move player in the downwards direction.
   */
  void moveDown();

  /**
   * Move player in the right direction.
   */
  void moveRight();

  /**
   * Move player in the left direction.
   */
  void moveLeft();

  /**
   * Pause game.
   */
  void pause();

  /**
   * Close open dialog box.
   */
  void closeDialog();

  /**
   * Discard current level and go to next.
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
   * Play level 1.
   */
  void startLevel1();

  /**
   * Play level 2.
   */
  void startLevel2();
}
