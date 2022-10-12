package test.nz.ac.vuw.ecs.swen225.gp22.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Color;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Door;
import nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    var maze = new Maze(tiles, 1, 1, 1);

    assertTrue(maze.getTiles().length == 1);
    assertTrue(maze.getInventory().size() == 0);
    assertTrue(maze.getRows() == 1);
    assertTrue(maze.getCols() == 1);
  }

  @Test
  public void canConstructWithInventory() {
    var tiles = new Tile[1][1];
    tiles[0][0] = new Chap();

    var maze = new Maze(tiles, 1, 1, List.of(), 1);

    assertTrue(maze.getTiles().length == 1);
    assertTrue(maze.getInventory().size() == 0);
    assertTrue(maze.getRows() == 1);
    assertTrue(maze.getCols() == 1);
  }

  @Test
  public void canSetupChap() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();

    var maze = new Maze(tiles, 4, 4, List.of(), 1);

    assertTrue(maze.getChapPosition().y() == 1);
    assertTrue(maze.getChapPosition().x() == 2);
  }

  @Test
  public void cannotConstructNullMaze() {
    var exception = Assertions.assertThrows(
      IllegalStateException.class, () -> {
      new Maze(null, 1, 2, null, 1);
    });

    Assertions.assertEquals("Tiles cannot be null", exception.getMessage());
  }

  @Test
  public void cannotConstructWithoutChap() {
    var exception = Assertions.assertThrows(
      IllegalStateException.class, () -> {
      new Maze(new Tile[1][1], 1, 1, 1);
    });

    Assertions.assertEquals("Must specify a single chap", exception.getMessage());
  }

  @Test
  public void canMoveChap() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();

    var maze = new Maze(tiles, 4, 4, List.of(), 1);

    assertTrue(maze.canMoveChap(Direction.Up));
    maze.moveChap(Direction.Up);

    var position = maze.getChapPosition();
    var direction = maze.getChapDirection();

    assertTrue(position.x() == 2);
    assertTrue(position.y() == 0);
    assertTrue(direction == Direction.Up);
  }

  @Test
  public void cannotMoveChap() {
    var tiles = new Tile[4][4];
    tiles[0][2] = new Wall();
    tiles[1][2] = new Chap();

    var maze = new Maze(tiles, 4, 4, List.of(), 1);

    assertFalse(maze.canMoveChap(Direction.Up));

    var exception = Assertions.assertThrows(
      IllegalArgumentException.class, () -> {
      maze.moveChap(Direction.Up);
    });

    Assertions.assertEquals("Cannot move chap in that direction", exception.getMessage());
  }

  @Test
  public void canPickupKey() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();
    tiles[2][2] = new Key(Color.Green);

    var maze = new Maze(tiles, 4, 4, List.of(), 1);

    maze.moveChap(Direction.Down);

    assertTrue(maze.getTilesOfType(Key.class).size() == 0);
    assertTrue(maze.getInventory().size() == 1);

    var key = (Key) maze.getInventory().get(0);
    assertTrue(key.color() == Color.Green);
  }

  @Test
  public void canPickupTreasure() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();
    tiles[2][2] = new Treasure();

    var maze = new Maze(tiles, 4, 4, 1);

    maze.moveChap(Direction.Down);

    assertTrue(maze.getTilesOfType(Treasure.class).size() == 0);
    assertTrue(maze.getInventory().size() == 1);
    assertTrue(maze.getInventory().get(0) instanceof Treasure);
  }

  @Test
  public void canOpenDoor() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();
    tiles[2][2] = new Door(Color.Blue);

    var maze = new Maze(tiles, 4, 4, List.of(new Key(Color.Blue)), 1);

    maze.moveChap(Direction.Down);

    assertTrue(maze.getTilesOfType(Door.class).size() == 0);
    assertTrue(maze.getInventory().size() == 0);
  }

  @Test
  public void cannotOpenDoor() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();
    tiles[2][2] = new Door(Color.Blue);

    var maze = new Maze(tiles, 4, 4, List.of(new Key(Color.Green)), 1);

    assertFalse(maze.canMoveChap(Direction.Down));

    var exception = Assertions.assertThrows(
      IllegalArgumentException.class, () -> {
      maze.moveChap(Direction.Down);
    });

    Assertions.assertEquals("Cannot move chap in that direction", exception.getMessage());
  }

  @Test
  public void canCountDown() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();

    var maze = new Maze(tiles, 4, 4, List.of(new Key(Color.Green)), 3);

    assertTrue(maze.getTimeLeft() == 3);

    maze.tick();

    assertTrue(maze.getTimeLeft() == 2);

    maze.tick();
    maze.tick();
    maze.tick();

    assertTrue(maze.getTimeLeft() == 0);
  }

  @Test
  public void canCountTiles() {
    var tiles = new Tile[4][4];
    tiles[0][0] = new Treasure();
    tiles[0][1] = new Treasure();
    tiles[1][2] = new Chap();
    tiles[2][0] = new Key(Color.Green);

    var maze = new Maze(tiles, 4, 4, List.of(new Key(Color.Green)), 3);

    assertTrue(maze.getCountOfMazeTiles(Treasure.class) == 2);
    assertTrue(maze.getCountOfMazeTiles(Key.class) == 1);
    assertTrue(maze.getCountOfMazeTiles(Door.class) == 0);
  }

  @Test
  public void cannotMoveWhenOutOfTime() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();

    var maze = new Maze(tiles, 4, 4, List.of(new Key(Color.Green)), 1);

    maze.tick();
    maze.tick();

    assertFalse(maze.canMoveChap(Direction.Down));

    var exception = Assertions.assertThrows(
      IllegalArgumentException.class, () -> {
      maze.moveChap(Direction.Down);
    });

    Assertions.assertEquals("Cannot move chap in that direction", exception.getMessage());
  }

  @Test
  public void canGetInfoText() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();
    tiles[0][2] = new Info("Test text");

    var maze = new Maze(tiles, 4, 4, List.of(new Key(Color.Green)), 1);

    assert(maze.getInfoText() == null);
    maze.moveChap(Direction.Up);
    assert(maze.getInfoText() == "Test text");
  }

  @Test
  public void canFinishOnExit() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();
    tiles[0][2] = new Exit();

    var maze = new Maze(tiles, 4, 4, List.of(), 1);

    assertFalse(maze.isGameOver());
    maze.moveChap(Direction.Up);
    assertTrue(maze.isGameOver());
  }

  @Test
  public void canFinishOnTime() {
    var tiles = new Tile[4][4];
    tiles[1][2] = new Chap();

    var maze = new Maze(tiles, 4, 4, List.of(), 1);

    assertFalse(maze.isGameOver());
    
    maze.tick();
    maze.tick();

    assertTrue(maze.isGameOver());
  }
}
