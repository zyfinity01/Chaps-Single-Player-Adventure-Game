package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Color;
import nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Lock;
import nz.ac.vuw.ecs.swen225.gp22.domain.Maze;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;


/**
 * Persistency module.
 *
 * @author  Niraj Gandhi, 300564849.
 */
public class Persistency {
  /**
  * This method is used to load the game status from the XML file.
  *
  * @param fileName name of input file
  */
  public static Maze loadGame(String fileName, int boardCols, int boardRows) {
    Tile[][] board = new Tile[boardRows][boardCols];
    //read XML file
    Document doc = getParsedDoc("src/levels/" + fileName);
    for (Element element : doc.getRootElement().getChildren()) {
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
                  case "TREASURE" -> board[x][y] = new Treasure();
                  case "WALL" -> board[x][y] = new Wall();
                  case "LOCK" -> board[x][y] = new Lock();
                  case "EXIT" -> board[x][y] = new Exit();
                  case "CHAP" -> board[x][y] = new Chap();
                  case "KEY" -> {
                    c = Color.valueOf(tile.getChild("color").getValue());
                    board[x][y] = new Key(c);
                  }
                  case "INFO" -> {
                    text = tile.getChild("text").getValue();
                    board[x][y] = new Info(text);
                  }
                  default -> {
                  }
                }
              }
              break;
            }
          }
          break;
        default:
          break;
      }
    }
    return new Maze(board, boardRows, boardCols);
  }
  
  private static Document getParsedDoc(final String fileName) {
    Document doc = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = factory.newDocumentBuilder();
      org.w3c.dom.Document w3cDocument = documentBuilder.parse(fileName);
      doc = new DOMBuilder().build(w3cDocument);
    } catch (IOException | SAXException | ParserConfigurationException e) {
      e.printStackTrace();
    }
    return doc;
  }
}
