package test.nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import org.junit.jupiter.api.Test;


/**
 * @author  Niraj Gandhi, 300564849.
 */

public class PersistencyTest {
    @Test
    public void test1() {
        Tile[][] board = new Tile[10][10];
        //iterate over board and create tile objects
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                board[i][j] = new Wall();
            }
        }
        new Persistency().saveGameStatus("test1.xml", "level1", 100, 10, 5, 3, board);

    }

    @Test
    public void test2() {
        // TODO

    }
}