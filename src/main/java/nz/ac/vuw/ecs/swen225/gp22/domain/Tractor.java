package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * The moving tractor tile.
 *
 * @author Jonty Morris, 300563915.
 */
public record Tractor(Direction direction) implements Tile {
  @Override
  public boolean canInteractWithPlayer(Tile[][] tiles, List<Tile> inventory) {
    return true;
  }

  @Override
  public void interactWithPlayer(Tile[][] tiles, List<Tile> inventory, Position position) {
    // game will end
  }

  @Override
  public void tick(Tile[][] tiles, Position position) {
    // find the next position
    var nextX = position.x() + direction.getX();
    var nextY = position.y() + direction.getY();

    // check if we should change direction
    if (nextX < 0 || nextY < 0 || nextY > tiles.length ||
        nextX >= tiles[nextY].length || tiles[nextY][nextX] != null) {
      tiles[position.y()][position.x()] = new Tractor(direction.opposite());
      return;
    }

    // otherwise move to that tile
    tiles[position.y()][position.x()] = null;
    tiles[nextY][nextX] = new Tractor(direction);
  }
}
