package nz.ac.vuw.ecs.swen225.gp22.persistency;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

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
    public void saveGameStatus(String fileName, String levelName, int time, int moves, int chips, int lives, Tile[][] board) {
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
                Tile operatingTile = board[i][j];
                Element tile = new Element("tile");
                tile.setText(operatingTile.getClass().toString());
                Field[] fields = operatingTile.getClass().getDeclaredFields();
                //add all fields to tile element and all values of the fields
                for (Field field : fields) {
                    field.setAccessible(true);
                    Element fieldElement = new Element(field.getName());
                    try {
                        //if field is of type list, then traverse through the list and add all elements to field element
                        if (field.getType().toString().contains("List")) {
                            List list = (List) field.get(operatingTile);
                            for (Object object : list) {
                                Element listElement = new Element("listElement");
                                listElement.setText(object.toString());
                                fieldElement.addContent(listElement);
                            }
                        }
                        //if field is of type int, then add value to field element
                        else if (field.getType().toString().contains("int")) {
                            fieldElement.setText(Integer.toString(field.getInt(operatingTile)));
                        }
                        //if field is of type boolean, then add value to field element
                        else if (field.getType().toString().contains("boolean")) {
                            fieldElement.setText(Boolean.toString(field.getBoolean(operatingTile)));
                        }
                        //if field is of type String, then add value to field element
                        else if (field.getType().toString().contains("String")) {
                            fieldElement.setText((String) field.get(operatingTile));
                        }
                        //if field is of type Map, traverse through the Map and add all key and value to field element
                        else if (field.getType().toString().contains("Map")) {
                        }
                        fieldElement.addContent(field.get(operatingTile).toString());

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    tile.addContent(fieldElement);
                }
                boardElement.addContent(tile);
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
