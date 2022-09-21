package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The door tile. Requires a key.
 *
 * @author Jonty Morris, 300563915.
 */
public record Door(Color color) implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    /*
     * return Stream.of(tiles)
      .flatMap(Stream::of)
      .filter(Key.class::isInstance)
      .map(Key.class::cast)
      .anyMatch(x -> x.color() == color());
     */

    // check player has the matching key
    return inventory.stream()
      .filter(Key.class::isInstance)
      .map(Key.class::cast)
      .anyMatch(x -> x.color() == color());
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    if (!canInteractWithPlayer(tiles, inventory)) {
      throw new IllegalStateException("Cannot open the door");
    }

    // remove the door
    tiles[position.y()][position.x()] = null;
  }
}
