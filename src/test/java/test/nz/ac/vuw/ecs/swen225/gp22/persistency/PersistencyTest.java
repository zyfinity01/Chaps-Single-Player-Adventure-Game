package test.nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author  Niraj Gandhi, 300564849.
 */

public class PersistencyTest {
//    @Test
//    public void test1() {
//        Persistency.saveGame("test5.xml", Persistency.loadGame("level1.xml", 17, 17));
//    }
//    @Test
//    public void test1() {
//        Tile[][] board = new Tile[10][10];
//        //iterate over board and create tile objects
//        for(int i = 0; i < 10; i++) {
//            for(int j = 0; j < 10; j++) {
//                board[i][j] = new Wall();
//            }
//        }
//        new Persistency().saveGameStatus("test1.xml", "level1", 100, 10, 5, 3, board);
//    }

//    @Test
//    public void test2() {
//        Maze myMaze = new Persistency().loadGame("level1.xml", 10, 10);
//        Tile[][] tiles = myMaze.getTiles();
//        //iterate over tiles length and width and print them
//        for(int i = 0; i < tiles.length; i++) {
//            for(int j = 0; j < tiles[i].length; j++) {
//                //System.out.print(tiles[i][j].getClass().getSimpleName() + " ");
//                if(tiles[i][j] == null) System.out.println("x:" + i + " y:" + j +" : null tile");
//                else System.out.println("x:" + i + " y:" + j + " : " + tiles[i][j].toString());
//            }
//        }
//
//
//    }
    @Test
    public void testWallLoading_01() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Creating json wall tile object
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <WALL>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "            </WALL>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        String fileName = "persistencyTest.xml";
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Wall);
    }

    public static Tile getTile(Maze maze, int x, int y) {
        Tile[][] tiles = maze.getTiles();
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                if(i == x && j == y) {
                    return tiles[i][j];
                }
            }
        }
        return null;
    }
    public static void toXml(String s) {
        try {
            FileOutputStream fileStream = new FileOutputStream("src/levels/persistencyTest.xml");
            OutputStreamWriter writer;
            writer = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
