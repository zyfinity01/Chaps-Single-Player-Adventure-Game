package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;
import java.util.stream.Stream;

/**
 * The lock tile. Requires all treasure.
 *
 * @author Jonty Morris, 300563915.
 */
public record Lock() implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // check there is no treasure left
    return Stream.of(tiles)
      .flatMap(Stream::of)
      .filter(Treasure.class::isInstance)
      .count() == 0;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    if (!canInteractWithPlayer(tiles, inventory)) {
      throw new IllegalStateException("Cannot interact with this lock");
    }

    // remove the lock
    tiles[position.y()][position.x()] = null;
  }
}
