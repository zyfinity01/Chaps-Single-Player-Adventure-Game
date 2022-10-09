package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The wall tile.
 *
 * @author Jonty Morris, 300563915.
 */
public record Wall() implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // can never go through a wall
    return false;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    throw new IllegalStateException("Cannot interact with the wall");
  }

  @Override
  public void tick(Tile[][] tiles, Position position) {
    // nothing to do on tick
  }
}
