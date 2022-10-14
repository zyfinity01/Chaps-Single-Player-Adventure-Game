package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Renderer;

/**
 * UI Container displaying game statistics.
 *
 * @author Sam Redmond, 300443508
 */
public class StatePanel extends JPanel {

  /**
   * Max number of items that can be in inventory.
   */
  private static final int maxInventorySpace = 12;

  /**
   * Size of icons in the inventory.
   */
  private static final int iconSize = 40;

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
  public StatePanel() {
    setLayout(new GridLayout(0, 1, 5, 5));
    setPreferredSize(new Dimension(220, 450));
    setOpaque(false);

    /*
     * Stats panel
     */
    statsPanel = new JPanel();
    statsPanel.setOpaque(false);
    statsPanel.setLayout(new GridLayout(4, 2));
    
    statsPanel.add(new FancyLabel("Level:"));
    currentLevel = new FancyLabel("0");
    statsPanel.add(currentLevel);
    
    statsPanel.add(new FancyLabel("Time Left:"));
    timeLeft = new FancyLabel("0.00");
    statsPanel.add(timeLeft);

    statsPanel.add(new FancyLabel("Keys Left:"));
    keysLeft = new FancyLabel("0");
    statsPanel.add(keysLeft);
    
    statsPanel.add(new FancyLabel("Chips Left:"));
    chipsLeft = new FancyLabel("0");
    statsPanel.add(chipsLeft);

    add(statsPanel);

    /*
     * Inventory panel
     */
    inventoryIcons = new ArrayList<>();

    inventoryPanel = new JPanel();
    inventoryPanel.setOpaque(false);
    inventoryPanel.setLayout(new GridLayout(0, 4, 0, 0));
    inventoryPanel.setBorder(new EmptyBorder(10, 20, 10, 10));
    
    for (int i = 0; i < maxInventorySpace; i++) {
      var icon = new JLabel();
      //icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      icon.setPreferredSize(new Dimension(iconSize, iconSize));
      inventoryIcons.add(icon);
      inventoryPanel.add(icon);
    }

    add(inventoryPanel);

  }

  /**
   * Set the current level on info panel.
   *
   * @param level current level
   */
  public void setLevel(int level) {
    currentLevel.setText(String.valueOf(level));
  }

  /**
   * Set the time left on info panel.
   *
   * @param time time left
   */
  public void setTime(double time) {
    timeLeft.setText(String.format("%.2fs", time));
  }

  /**
   * Set the chips left on info panel.
   *
   * @param chips chips left
   */
  public void setChipsLeft(int chips) {
    chipsLeft.setText(String.valueOf(chips));
  }

  /**
   * Set the keys left on info panel.
   *
   * @param keys keys left
   */
  public void setKeysLeft(int keys) {
    keysLeft.setText(String.valueOf(keys));
  }

  /**
   * Display the items in the inventory.
   *
   * @param inventory inventory to display
   */
  public void setInventory(List<Tile> inventory) {
    for (int i = 0; i < maxInventorySpace; i++) {
      var icon = inventoryIcons.get(i);
      var tile = i < inventory.size() ? inventory.get(i) : null;
      if (tile != null) {
        var image = Renderer.getTileImage(tile)
            .getScaledInstance(iconSize, iconSize, Image.SCALE_FAST);
        icon.setIcon(new ImageIcon(image));
      } else {
        icon.setIcon(null);
      }
    }
  }

}
