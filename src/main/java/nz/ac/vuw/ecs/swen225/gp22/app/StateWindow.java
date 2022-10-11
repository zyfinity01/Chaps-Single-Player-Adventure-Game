package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * UI Container displaying game statistics.
 *
 * @author Sam Redmond, 300443508
 */
public class StateWindow extends JPanel {

  /**
   * Max number of items that can be in inventory.
   */
  private final static int MAX_INVENTORY_SIZE = 8;

  /**
   * The games current level.
   */
  private JLabel currentLevel;

  /**
   * Countdown till game over.
   */
  private JLabel timeLeft;

  /**
   * Count of chips remaining in level.
   */
  private JLabel chipsLeft;

  /**
   * Count of keys remaining in level.
   */
  private JLabel keysLeft;

  /**
   * Icons for each item in inventory.
   */
  private List<JLabel> inventoryIcons;

  /**
   * Panel holding the inventory icons.
   */
  private JPanel inventoryPanel;

  /**
   * Panel holding the level information.
   */
  private JPanel statsPanel;

  /**
   * Displays Game Information.
   */
  public StateWindow() {
    setLayout(new GridLayout(0, 1));

    statsPanel = new JPanel();
    statsPanel.setLayout(new GridLayout(4, 2, 5, 10));
    
    statsPanel.add(new JLabel("Level:"));
    currentLevel = new JLabel("0");
    statsPanel.add(currentLevel);
    
    statsPanel.add(new JLabel("Time Left:"));
    timeLeft = new JLabel("0.00");
    statsPanel.add(timeLeft);

    statsPanel.add(new JLabel("Keys Left:"));
    keysLeft = new JLabel("0");
    statsPanel.add(keysLeft);
    
    statsPanel.add(new JLabel("Chips Left:"));
    chipsLeft = new JLabel("0");
    statsPanel.add(chipsLeft);

    add(statsPanel);

    inventoryPanel = new JPanel();
    inventoryPanel.setLayout(new GridLayout(0, 4));
    inventoryIcons = new ArrayList<>();
    for (int i = 0; i < MAX_INVENTORY_SIZE; i++) {
      var icon = new JLabel();
      icon.setPreferredSize(new Dimension(50, 50));
      inventoryIcons.add(icon);
      inventoryPanel.add(icon);
    }

    add(inventoryPanel);

  }

  /**
   * Set the current level on info panel.
   * @param level current level
   */
  public void setLevel(int level) {
    currentLevel.setText(String.valueOf(level));
  }

  /**
   * Set the time left on info panel.
   * @param time time left
   */
  public void setTime(double time) {
    timeLeft.setText(String.format("%.2fs", time));
  }

  /**
   * Set the chips left on info panel.
   * @param chips chips left
   */
  public void setChipsLeft(int chips) {
    chipsLeft.setText(String.valueOf(chips));
  }

  /**
   * Set the keys left on info panel.
   * @param keys keys left
   */
  public void setKeysLeft(int keys) {
    keysLeft.setText(String.valueOf(keys));
  }

  /**
   * Display the items in the inventory
   * @param inventory inventory to display
   */
  public void setInventory(List<Tile> inventory) {
    for (int i = 0; i < inventory.size(); i++) {
      var tile = inventory.get(i);
      var icon = inventoryIcons.get(i);
      if (tile != null) {
        icon.setIcon(new ImageIcon(Renderer.getTileImage(tile)));
      } else {
        icon.setIcon(null);
      }
    }
  }

}
