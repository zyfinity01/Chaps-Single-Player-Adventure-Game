package test.nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import org.jdom2.Document;
import org.junit.jupiter.api.Test;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


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
    //Save toXML to file
    private static final String fileName = "src/levels/persistencyTest.xml";
    private static final String saveGameFileName = "src/levels/saveGamePersistencyTest.xml";

    // =================================================================
    // Loading Tests
    // =================================================================
    @Test
    public void testWallLoading() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Creating wall tile object
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
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Wall);
    }

    @Test
    public void testFreeLoading() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if free tile is loaded correctly
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
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertNull(getTile(maze, 6, 6));
    }

    @Test
    public void testKeyLoading() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if key tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <KEY>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "                <color>" + Color.Red + "</color>\n" +
                "            </KEY>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Key);
        assertEquals(((Key) getTile(maze, x, y)).color(), Color.Red);
    }

    @Test
    public void testInfoLoading() {
        int x = 9;
        int y = 7;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        String inputText = "This is a test";
        // Checking if info tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <INFO>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "                <text>" + inputText + "</text>\n" +
                "            </INFO>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Info);
        String tileText = ((Info) (getTile(maze, x, y))).text();
        System.out.println(tileText);
        assertEquals(tileText, inputText);
    }

    @Test
    public void testTreasureLoading() {
        int x = 13;
        int y = 4;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if treasure tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <TREASURE>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "            </TREASURE>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Treasure);
    }

    @Test
    public void testExitLockLoading() {
        int x = 16;
        int y = 2;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if exit lock tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <LOCK>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "            </LOCK>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Lock);
    }

    @Test
    public void testLockedDoorLoading() {
        int x = 13;
        int y = 8;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if locked door tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <DOOR>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "                <color>" + Color.Green + "</color>\n" +
                "            </DOOR>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Door);
        assertEquals(((Door) getTile(maze, x, y)).color(), Color.Green);
    }

    @Test
    public void testExitLoading() {
        int x = 12;
        int y = 9;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if exit tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <EXIT>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "            </EXIT>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertTrue(getTile(maze, x, y) instanceof Exit);
    }


    @Test
    public void testChapLoading() {
        int x = 12;
        int y = 9;
        int chapX = 5;
        int chapY = 13;
        int col = 17;
        int row = 17;
        // Checking if chap tile is loaded correctly
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <EXIT>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "            </EXIT>\n" +
                "            <CHAP>\n" +
                "                <x>" + chapX + "</x>\n" +
                "                <y>" + chapY + "</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        System.out.println(Arrays.deepToString(maze.getTiles()));
        assertEquals(maze.getChapPosition(), new Position(chapX, chapY));
    }

    @Test
    public void testMazeLoadingWithoutChap() {
        int x = 12;
        int y = 9;
        int chapX = 5;
        int chapY = 13;
        int col = 17;
        int row = 17;
        // Checking if chap is not loaded
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <EXIT>\n" +
                "                <x>" + x + "</x>\n" +
                "                <y>" + y + "</y>\n" +
                "            </EXIT>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        try {
            Persistency.loadGame(fileName, col, row);
            fail("No exception thrown for missing chap");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "Must specify a single chap");
        }
    }

    // =================================================================
    // Saving Tests
    // =================================================================
    @Test
    public void testWallSaving() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // Checking if wall tile is saved correctly alongside the document as a whole
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
        Maze maze = Persistency.loadGame(fileName, col, row);
        Persistency.saveGame(saveGameFileName, maze);
        Document inputDocument = Persistency.getParsedDoc(fileName);
        Document saveGameDocument = Persistency.getParsedDoc(saveGameFileName);
        //inputDocument.equals(saveGameDocument);
        assertEquals(inputDocument.toString(), saveGameDocument.toString());
    }

    // =================================================================
    // Saving Tests
    // =================================================================
    @Test
    public void testComplexSaving() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // test saving of a complex real game style scenario
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <time>60</time>\n" +
                "    <inventory>\n" +
                "        <KEY>\n" +
                "            <color>Blue</color>\n" +
                "        </KEY>\n" +
                "        <TREASURE />\n" +
                "    </inventory>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <INFO>\n" +
                "                <x>9</x>\n" +
                "                <y>7</y>\n" +
                "                <text>This is where info goes</text>\n" +
                "            </INFO>\n" +
                "            <KEY>\n" +
                "                <x>4</x>\n" +
                "                <y>6</y>\n" +
                "                <color>Yellow</color>\n" +
                "            </KEY>\n" +
                "            <EXIT>\n" +
                "                <x>9</x>\n" +
                "                <y>4</y>\n" +
                "            </EXIT>\n" +
                "            <LOCK>\n" +
                "                <x>9</x>\n" +
                "                <y>5</y>\n" +
                "            </LOCK>\n" +
                "            <DOOR>\n" +
                "                <x>10</x>\n" +
                "                <y>11</y>\n" +
                "                <color>Yellow</color>\n" +
                "            </DOOR>\n" +
                "            <TREASURE>\n" +
                "                <x>10</x>\n" +
                "                <y>13</y>\n" +
                "            </TREASURE>\n" +
                "            <CHAP>\n" +
                "                <x>9</x>\n" +
                "                <y>8</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        Persistency.saveGame(saveGameFileName, maze);
        Document inputDocument = Persistency.getParsedDoc(fileName);
        Document saveGameDocument = Persistency.getParsedDoc(saveGameFileName);
        //inputDocument.equals(saveGameDocument);
        assertEquals(inputDocument.toString(), saveGameDocument.toString());
    }

    // =================================================================
    // Test actor loading and saving
    // =================================================================
    @Test
    public void testLoadCustomActor(){
        //Test if Actor loads correctly and direction is detected
        Tile customActor = Persistency.newCustomActor("./src/levels/level2.jar", Direction.Up);
        assertEquals(Direction.Up, Persistency.getCustomActorDirection(customActor));
    }

    @Test
    public void testComplexSavingActor() {
        int x = 5;
        int y = 5;
        int chapX = 1;
        int chapY = 1;
        int col = 17;
        int row = 17;
        // test saving and loading of a complex real game style scenario with an actor
        toXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<game>\n" +
                "    <time>60</time>\n" +
                "    <inventory>\n" +
                "        <KEY>\n" +
                "            <color>Blue</color>\n" +
                "        </KEY>\n" +
                "        <TREASURE />\n" +
                "    </inventory>\n" +
                "    <board>\n" +
                "        <tiles>\n" +
                "            <ACTOR>\n" +
                "                <x>8</x>\n" +
                "                <y>5</y>\n" +
                "                <direction>Up</direction>\n" +
                "            </ACTOR>" +
                "            <INFO>\n" +
                "                <x>9</x>\n" +
                "                <y>7</y>\n" +
                "                <text>This is where info goes</text>\n" +
                "            </INFO>\n" +
                "            <KEY>\n" +
                "                <x>4</x>\n" +
                "                <y>6</y>\n" +
                "                <color>Yellow</color>\n" +
                "            </KEY>\n" +
                "            <EXIT>\n" +
                "                <x>9</x>\n" +
                "                <y>4</y>\n" +
                "            </EXIT>\n" +
                "            <LOCK>\n" +
                "                <x>9</x>\n" +
                "                <y>5</y>\n" +
                "            </LOCK>\n" +
                "            <DOOR>\n" +
                "                <x>10</x>\n" +
                "                <y>11</y>\n" +
                "                <color>Yellow</color>\n" +
                "            </DOOR>\n" +
                "            <TREASURE>\n" +
                "                <x>10</x>\n" +
                "                <y>13</y>\n" +
                "            </TREASURE>\n" +
                "            <CHAP>\n" +
                "                <x>9</x>\n" +
                "                <y>8</y>\n" +
                "            </CHAP>\n" +
                "        </tiles>\n" +
                "    </board>\n" +
                "</game>\n");
        Maze maze = Persistency.loadGame(fileName, col, row);
        Persistency.saveGame(saveGameFileName, maze);
        Document inputDocument = Persistency.getParsedDoc(fileName);
        Document saveGameDocument = Persistency.getParsedDoc(saveGameFileName);
        //inputDocument.equals(saveGameDocument);
        assertEquals(inputDocument.toString(), saveGameDocument.toString());
    }

    public static Tile getTile(Maze maze, int x, int y) {
        Tile[][] tiles = maze.getTiles();
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                if(i == x && j == y) {
                    return tiles[j][i];
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
