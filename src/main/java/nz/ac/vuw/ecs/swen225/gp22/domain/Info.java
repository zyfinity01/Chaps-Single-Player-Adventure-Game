package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The info tile. Displays some text.
 *
 * @author Jonty Morris, 300563915.
 */
public record Info(String text) implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    // can always stand on info
    return true;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    // no interaction to perform
  }

  @Override
  public void tick(Tile[][] tiles, Position position) {
    // nothing to do on tick
  }
}
