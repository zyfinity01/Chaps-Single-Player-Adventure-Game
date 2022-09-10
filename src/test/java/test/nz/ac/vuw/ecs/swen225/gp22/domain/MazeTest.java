package test.nz.ac.vuw.ecs.swen225.gp22.domain;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

/**
* Unit test for the Maze
*/
public class MazeTest {
  @Test
  public void canConstructWithoutInventory() {
    var tiles = new Tile[0][0];
    var maze = new Maze(tiles, 0, 0);

    assertTrue(maze.getTiles().length == 0);
    assertTrue(maze.getInventory().size() == 0);
    assertTrue(maze.getRows() == 0);
    assertTrue(maze.getCols() == 0);
  }

  @Test
  public void canConstructWithInventory() {
    var tiles = new Tile[0][0];
    var maze = new Maze(tiles, 0, 0, List.of());

    assertTrue(maze.getTiles().length == 0);
    assertTrue(maze.getInventory().size() == 0);
    assertTrue(maze.getRows() == 0);
    assertTrue(maze.getCols() == 0);
  }
}
