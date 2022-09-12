package nz.ac.vuw.ecs.swen225.gp22.persistency;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author  Niraj Gandhi, 300564849.
 */
public class Persistency {


    /**
     * This method is used to save the game status in the form of XML file.
     * @param fileName name of output file
     * @param levelName name of level - String or Int to be decided
     * @param time Either time taken to complete level or time left as spare
     * @param moves number of moves taken to complete level
     * @param chips number of computer chips collected
     * @param lives number of lives left
     * @param board 2D array of tiles
     */
    public void saveGameStatus(String fileName, String levelName, int time, int moves, int chips, int lives, String[][] board) {
        //create root element
        Element root = new Element("game");
        //create document
        Document doc = new Document(root);
        //create child elements
        Element level = new Element("level");
        Element timeElement = new Element("time");
        Element movesElement = new Element("moves");
        Element chipsElement = new Element("chips");
        Element livesElement = new Element("lives");
        Element boardElement = new Element("board");
        //add child elements to root element
        root.addContent(level);
        root.addContent(timeElement);
        root.addContent(movesElement);
        root.addContent(chipsElement);
        root.addContent(livesElement);
        root.addContent(boardElement);
        //add values to child elements
        level.setText(levelName);
        timeElement.setText(Integer.toString(time));
        movesElement.setText(Integer.toString(moves));
        chipsElement.setText(Integer.toString(chips));
        livesElement.setText(Integer.toString(lives));
        //add board to board element
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Element cell = new Element("cell");
                cell.setText(board[i][j]);
                boardElement.addContent(cell);
            }
        }
        //save document to XML file
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
