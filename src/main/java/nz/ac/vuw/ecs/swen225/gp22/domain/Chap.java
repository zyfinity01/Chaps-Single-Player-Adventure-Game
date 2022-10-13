package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The player tile. Can be moved around.
 *
 * @author Jonty Morris, 300563915.
 */
public record Chap() implements Tile {
  @Override
  public BufferedImage getCustomImage() {
    // not used by renderer
    return null;
  }

  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // cannot stand on player
    return false;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    throw new IllegalStateException("Cannot interact with chap");
  }

  @Override
  public void tick(Tile[][] tiles, Position position) {
    // nothing to do on tick
  }
}
