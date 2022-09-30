package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The key tile. Comes in different colours.
 *
 * @author Jonty Morris, 300563915.
 */
public record Key(Color color) implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // can always pick up a key
    return true;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    // move key to inventory
    inventory.add(this);
    tiles[position.y()][position.x()] = null;
  }
}