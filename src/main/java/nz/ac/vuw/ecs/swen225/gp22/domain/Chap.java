package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The player tile. Can be moved around.
 *
 * @author Jonty Morris, 300563915.
 */
public record Chap() implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // cannot stand on player
    return false;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    throw new IllegalStateException("Cannot interact with chap");
  }
}
