package nz.ac.vuw.ecs.swen225.gp22.app;

//import java.awt.event.ItemEvent;
//import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.*;

import javax.swing.JFrame;
//import javax.swing.JPanel;

// check the above imports - simply pulled them from the ones I used in A1. Not all will be used but put them there for simplicity. 

public class App extends JFrame implements KeyListener {

  // double check visibility :-)
  private int timeLeftToPlay;
  private int numKeysCollected;
  private int numTreasureLeft;

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyPressed(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO

  }

  /**
   * Gets the time left to play the game.
   * 
   * @return timeLeftToPlay
   */
  public int getTimeLeft() {
    return timeLeftToPlay;
  }

  /**
   * Gets the number of keys collected.
   * 
   * @return numKeysCollected
   */
  public int getNumKeysCollected() {
    return numKeysCollected;
  }

  /**
   * Gets the number of chips(treasure) left.
   * 
   * @return numTreasureLeft
   */
  public int getNumTreasureLeft() {
    return numTreasureLeft;
  }

}
