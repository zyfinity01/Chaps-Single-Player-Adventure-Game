package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The treasure tile. Must be picked up.
 *
 * @author Jonty Morris, 300563915.
 */
public record Treasure() implements Tile {
  @Override
  public BufferedImage getCustomImage() {
    // not used by renderer
    return null;
  }

  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // can always pickup trasure
    return true;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    // move treasure to inventory
    inventory.add(this);
    tiles[position.y()][position.x()] = null;
  }

  @Override
  public void tick(Tile[][] tiles, Position position) {
    // nothing to do on tick
  }
}
