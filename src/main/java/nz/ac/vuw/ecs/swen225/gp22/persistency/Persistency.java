package nz.ac.vuw.ecs.swen225.gp22.persistency;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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
     *
     * @param fileName  name of output file
     * @param levelName name of level - String or Int to be decided
     * @param time      Either time taken to complete level or time left as spare
     * @param moves     number of moves taken to complete level
     * @param chips     number of computer chips collected
     * @param lives     number of lives left
     * @param board     2D array of tiles
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
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        Element fieldElement = new Element(field.getName());
                        fieldElement.setText(field.get(operatingTile).toString());
                        tile.addContent(fieldElement);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                Element boardLocation = new Element("boardLocation");
                boardLocation.addContent(i + "," + j);
                tile.addContent(boardLocation);
                //save document to XML file
                try {
                    XMLOutputter xmlOutput = new XMLOutputter();
                    xmlOutput.setFormat(Format.getPrettyFormat());
                    xmlOutput.output(doc, new FileWriter(new File("src/levels/", "level1.xml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is used to load the game status from the XML file.
     *
     * @param fileName name of input file
     */
    public Maze loadGame(String fileName, int boardCols, int boardRows) {
        Tile[][] board = new Tile[boardRows][boardCols];
        //read XML file
        Document doc = getParsedDoc("src/levels/"+fileName);
        for(Element element : doc.getRootElement().getChildren()){
            System.out.println(element.getName() + " " + element.getText());
            switch (element.getName()) {
                //TODO
                case "level":
                    break;
                case "time":
                    break;
                case "moves":
                    break;
                case "chips":
                    break;
                case "lives":
                    break;
                case "board":
                    List<Element> boardElements = element.getChildren();
                    for (Element item : boardElements) {
                        if (item.getName().equals("tiles")) {
                            List<Element> tileElements = item.getChildren();
                            for (Element tile : tileElements) {
                                int x = Integer.parseInt(tile.getChild("x").getValue());
                                int y = Integer.parseInt(tile.getChild("y").getValue());
                                Color c;
                                String text;
                                System.out.println(tile.getName());
                                switch (tile.getName()) {
                                    case "WALL" -> board[x][y] = new Wall();
                                    case "KEY" -> {
                                        c = Color.valueOf(tile.getChild("color").getValue());
                                        board[x][y] = new Key(c);
                                    }
                                    case "LOCK" -> board[x][y] = new Lock();
                                    case "INFO" -> {
                                        text = tile.getChild("text").getValue();
                                        board[x][y] = new Info(text);
                                    }
                                    case "TREASURE" -> board[x][y] = new Treasure();
                                    case "EXIT" -> board[x][y] = new Exit();
                                    case "CHAP" -> board[x][y] = new Chap();
                                }
                            }
                            break;
                        }
                    }
            }
        }
        return new Maze(board, boardRows, boardCols);
    }

    private static Document getParsedDoc(final String fileName)
    {
        Document doc = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            org.w3c.dom.Document w3cDocument = documentBuilder.parse(fileName);
            doc = new DOMBuilder().build(w3cDocument);
        }
        catch (IOException | SAXException | ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        return doc;
    }
}
