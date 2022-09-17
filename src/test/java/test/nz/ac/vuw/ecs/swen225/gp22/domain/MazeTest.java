package test.nz.ac.vuw.ecs.swen225.gp22.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

/**
* Unit test for the Maze.
* 
* @author Jonty Morris, 300563915.
*/
public class MazeTest {
  @Test
  public void canConstructWithoutInventory() {
    var tiles = new Tile[1][1];
    tiles[0][0] = new Chap();

    var maze = new Maze(tiles, 1, 1);

    assertTrue(maze.getTiles().length == 1);
    assertTrue(maze.getInventory().size() == 0);
    assertTrue(maze.getRows() == 1);
    assertTrue(maze.getCols() == 1);
  }

  @Test
  public void canConstructWithInventory() {
    var tiles = new Tile[1][1];
    tiles[0][0] = new Chap();

    var maze = new Maze(tiles, 1, 1, List.of());

    assertTrue(maze.getTiles().length == 1);
    assertTrue(maze.getInventory().size() == 0);
    assertTrue(maze.getRows() == 1);
    assertTrue(maze.getCols() == 1);
  }

  @Test
  public void cannotConstructNullMaze() {
    var exception = Assertions.assertThrows(
      IllegalArgumentException.class, () -> {
      new Maze(null, 1, 2, null);
    });
    Assertions.assertEquals("Tiles cannot be null", exception.getMessage());
  }

  @Test
  public void cannotConstructWithoutChap() {
    var exception = Assertions.assertThrows(
      IllegalStateException.class, () -> {
      new Maze(new Tile[1][1], 1, 1);
    });
    Assertions.assertEquals("Must specify a single chap", exception.getMessage());
  }
}
