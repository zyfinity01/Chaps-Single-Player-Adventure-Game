package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.Color;
import nz.ac.vuw.ecs.swen225.gp22.domain.Door;
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
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;


/**
 * Persistency module.
 *
 * @author  Niraj Gandhi, 300564849.
 */
public class Persistency {

  /**
   * Save the maze to a file.
   *
   * @param maze maze to save.
   * @param fileName file to save to.
   * @throws IOException if file cannot be written to.
   */
  public static void saveGame(String fileName, Maze maze) {
    Element root = new Element("Game");
    Document doc = new Document(root);
    Element timeElement = new Element("time");
    //Element livesElement = new Element("lives");
    Element inventoryElement = new Element("inventory");
    Element boardElement = new Element("board");

    root.addContent(timeElement);
    root.addContent(inventoryElement);
    //root.addContent(livesElement);
    root.addContent(boardElement);

    timeElement.setText(Integer.toString(maze.getTimeLeft()));
    //livesElement.setText(Integer.toString(maze.getLivesLeft()));

    for (Tile tile : maze.getInventory()) {
      String tileType = tile.getClass().getSimpleName().toUpperCase();
      Element tileElement = new Element(tileType);
      switch (tileType) {
        case "KEY" -> {
          Element tileColorElement = new Element("color");
          tileElement.addContent(tileColorElement.setText(((Key) tile).color().toString()));
        }
        default -> {
        }
      }
      inventoryElement.addContent(tileElement);
    }
    Tile[][] board2 = maze.getTiles();
    for (int row = 0; row < maze.getRows(); row++) {
      for (int col = 0; col < maze.getCols(); col++) {
        if (board2[row][col] != null) {
          System.out.println(board2[row][col].getClass().getSimpleName().toUpperCase());
        }
      }
    }

    Element tiles = new Element("tiles");
    boardElement.addContent(tiles);
    Tile[][] board = maze.getTiles();
    for (int row = 0; row < maze.getRows(); row++) {
      for (int col = 0; col < maze.getCols(); col++) {
        Tile tile = board[row][col];
        if (tile == null) {
          continue;
        }
        String tileType = tile.getClass().getSimpleName().toUpperCase();
        Element tileElement = new Element(tileType);
        tiles.addContent(tileElement);
        tileElement.addContent(new Element("x").setText(Integer.toString(col)));
        tileElement.addContent(new Element("y").setText(Integer.toString(row)));
        switch (tileType) {
          case "DOOR" -> tileElement
                  .addContent(new Element("color")
                          .setText(((Door) tile).color().toString()));
          case "KEY" -> tileElement
                  .addContent(new Element("color")
                          .setText(((Key) tile).color().toString()));
          case "INFO" -> tileElement
                  .addContent(new Element("text")
                          .setText(((Info) tile).text()));
          default -> {
          }
        }
      }
    }
    Element chapElement = new Element("CHAP");
    tiles.addContent(chapElement);
    chapElement.addContent(new Element("x").setText(String.valueOf(maze.getChapPosition().x())));
    chapElement.addContent(new Element("y").setText(String.valueOf(maze.getChapPosition().y())));
    //save document to XML file
    try {
      XMLOutputter xmlOutput = new XMLOutputter();
      xmlOutput.setFormat(Format.getPrettyFormat());
      xmlOutput.output(doc, new FileWriter("src/levels/" + fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Load a maze from a file.
   *
   * @param fileName file to load from.
   * @return loaded maze.
   * @throws IOException if file cannot be read from.
   * @throws ParserConfigurationException if XML parser cannot be configured.
   * @throws SAXException if XML cannot be parsed.
   */
  public static Maze loadGame(String fileName, int boardCols, int boardRows) {
    Tile[][] board = new Tile[boardRows][boardCols];
    //read XML file
    Document doc = getParsedDoc("src/levels/" + fileName);
    int  timeLeft = 60; //Default time
    List<Tile> inventory = new ArrayList<>();
    for (Element element : doc.getRootElement().getChildren()) {
      //System.out.println(element.getName() + " " + element.getText());
      switch (element.getName()) {
        case "time" -> timeLeft = Integer.parseInt(element.getValue());
        case "inventory" -> {
          List<Element> inventoryElements = element.getChildren();
          for (Element item : inventoryElements) {
            switch (item.getName()) {
              case "KEY" -> inventory.add(new Key(Color.valueOf(item.getChildText("color"))));
              case "TREASURE" -> inventory.add(new Treasure());
              default -> {
              }
            }
          }
        }
        case "board" -> {
          List<Element> boardElements = element.getChildren();
          for (Element item : boardElements) {
            if (item.getName().equals("tiles")) {
              List<Element> tileElements = item.getChildren();
              for (Element tile : tileElements) {
                int x = Integer.parseInt(tile.getChild("x").getValue());
                int y = Integer.parseInt(tile.getChild("y").getValue());
                Color c;
                String text;
                //System.out.println(tile.getName());
                switch (tile.getName()) {
                  case "TREASURE" -> board[y][x] = new Treasure();
                  case "WALL" -> board[y][x] = new Wall();
                  case "LOCK" -> board[y][x] = new Lock();
                  case "EXIT" -> board[y][x] = new Exit();
                  case "CHAP" -> board[y][x] = new Chap();
                  case "KEY" -> {
                    c = Color.valueOf(tile.getChild("color").getValue());
                    board[y][x] = new Key(c);
                  }
                  case "INFO" -> {
                    text = tile.getChild("text").getValue();
                    board[y][x] = new Info(text);
                  }
                  case "DOOR" -> {
                    c = Color.valueOf(tile.getChild("color").getValue());
                    board[y][x] = new Door(c);
                  }
                  default -> {
                  }
                }
              }
              break;
            }
          }
        }
        default -> {
        }
      }
    }
    return new Maze(board, boardRows, boardCols, inventory, timeLeft);
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
