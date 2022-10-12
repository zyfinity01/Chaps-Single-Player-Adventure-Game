package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;
import java.util.stream.Stream;

/**
 * The door tile. Requires a key.
 *
 * @author Jonty Morris, 300563915.
 */
public record Door(Color color) implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // check player has the matching key
    return getMatchingInventoryKeys(inventory).findAny().isPresent();
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    if (!canInteractWithPlayer(tiles, inventory)) {
      throw new IllegalStateException("Cannot open the door");
    }

    // remove the door
    tiles[position.y()][position.x()] = null;

    // remove the 1st matching key from inventory
    var key = getMatchingInventoryKeys(inventory).findFirst().get();
    inventory.remove(key);
  }

  @Override
  public void tick(Tile[][] tiles, Position position) {
    // nothing to do on tick
  }

  /**
   * Finds all matching keys in the inventory.
   *
   * @param inventory player inventory.
   * @return matching player inventory keys.
   */
  private Stream<Key> getMatchingInventoryKeys(List<Tile> inventory) {
    return inventory.stream()
      .filter(Key.class::isInstance)
      .map(Key.class::cast)
      .filter(x -> x.color() == color());
  }
}
