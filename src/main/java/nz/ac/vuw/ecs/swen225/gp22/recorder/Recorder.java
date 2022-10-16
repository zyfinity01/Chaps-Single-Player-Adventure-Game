package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;


/**
 * Recorder object saves, stores and exports all movements made by users and players.
 * $ @author Shae West, 300565911
 */
public class Recorder {
  private ArrayList<Entry<Integer, String>> playerMovements;
  private ArrayList<Entry<Integer, String>> actorMovements;
  private int levelNumber;
  private int tick;
  private HashMap<Integer, Direction> replayedPlayerMovements;
  private HashMap<Integer, Direction> replayedActorMovements;

  /**
   * Creates an Recorder,
   * If recordingName matches any current .XML files, load Recording from file.
   * Else, create a new empty Recorder object to be saved at a later date.
   * $ @param levelNumber Determines if actor movements are saved && what file they are saved to.
   * $ @param load If true, load from XML
   * $ @param xmlPath String path of file to load from
   */
  
  public Recorder(int levelNumber, boolean load, String xmlPath) {
    this.playerMovements = new ArrayList<>();
    this.actorMovements = new ArrayList<>();
    this.levelNumber = levelNumber;
    tick = 0;
    if (load && xmlPath != null) {
      this.loadToXml(levelNumber, xmlPath);
    }
  }

  /**
   * Adds a player movement to storage.
   * $ @param keyCode Integer value of keybind press 
   */
  public void savePlayerMovement(int keyCode) {
    Integer[] keyArray = new Integer[] {KeyEvent.VK_UP, KeyEvent.VK_LEFT, 
        KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN};
    List<Integer> validKeys = new ArrayList<Integer>(Arrays.asList(keyArray));
    if (!validKeys.contains(keyCode)) {
      return;
    }
    String directionString;
    switch (keyCode) {
      case KeyEvent.VK_UP:
        directionString = "up";
        break;
      case KeyEvent.VK_LEFT:
        directionString = "left";
        break;
      case KeyEvent.VK_DOWN:
        directionString = "down";
        break;
      case KeyEvent.VK_RIGHT:
        directionString = "right";
        break;
      default:
        directionString = "empty";
        break;
    }
    Map.Entry<Integer, String> entry = 
        new AbstractMap.SimpleEntry<Integer, String>(tick, directionString);
    this.playerMovements.add(entry);
    this.saveToXml();
  }

  /**
   * Adds a actor movement to storage.
   * $ @param direction Actor direction
   */
  public void saveActorMovement(Direction direction) {
    String directionString;
    switch (direction) {
      case Up:
        directionString = "up";
        break;
      case Left:
        directionString = "left";
        break;
      case Right:
        directionString = "right";
        break;
      case Down:
        directionString = "empty";
        break;
      default:
        directionString = "empty";
        break;
    }
    
    Map.Entry<Integer, String> entry = 
        new AbstractMap.SimpleEntry<Integer, String>(tick, directionString);
    this.actorMovements.add(entry);
    this.saveToXml();
  }

  /**
   * Saves the current savedSnapshots to XML format.
   */
  public void saveToXml() {
    Element root = new Element("root");
    
    Element playerMovements = new Element("PlayerMovements");
    Element actorMovements = new Element("ActorMovements");

    for (Map.Entry<Integer, String> entry : this.playerMovements) {
      Integer tickCollect = entry.getKey();
      String directionString = entry.getValue();

      Element movement = new Element("Movement");
      Element direction = new Element("Direction").setText(directionString);
      Element tick = new Element("Tick").setText(tickCollect.toString());

      movement.addContent(direction);
      movement.addContent(tick);

      playerMovements.addContent(movement);
    }
    root.addContent(playerMovements);

    if (this.levelNumber == 2) {
      for (Map.Entry<Integer, String> entry : this.actorMovements) {
        Integer tickCollect = entry.getKey();
        String directionString = entry.getValue();

        Element movement = new Element("Movement");
        Element direction = new Element("Direction").setText(directionString);
        Element tick = new Element("Tick").setText(tickCollect.toString());

        movement.addContent(direction);
        movement.addContent(tick);

        actorMovements.addContent(movement);
      }
      root.addContent(actorMovements);
    }
    Document document = new Document();
    document.setContent(root);

    try {
      FileOutputStream outstream = new FileOutputStream("moves_level_" + this.levelNumber + ".xml");
      OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
      XMLOutputter outputter = new XMLOutputter();
      outputter.setFormat(Format.getPrettyFormat());
      outputter.output(document, writer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads all movements based off level number.
   * $ @param level Determines which moves' file to load
   * $ @param xmlString the xmlString to load.
   */
  private void loadToXml(int level, String xmlString) {
    Document doc = Persistency.getParsedDoc(xmlString);
    this.replayedPlayerMovements = new HashMap<Integer, Direction>();
    Element playerMovementsElement =  doc.getRootElement().getChild("PlayerMovements");
    saveMovesToHashMap(playerMovementsElement, this.replayedPlayerMovements);
    
    if (level == 2) {
      this.replayedActorMovements = new HashMap<Integer, Direction>();
      Element actorMovementsElement = doc.getRootElement().getChild("ActorMovements");
      saveMovesToHashMap(actorMovementsElement, this.replayedActorMovements);
    }
  }

  /**
   * Converts a element of movements into formatted hashmap.
   * $ @param element Document element
   * $ @param hashMap HashMap to put into
   */
  private void saveMovesToHashMap(Element element, HashMap<Integer, Direction> hashMap) {
    for (Element subElement : element.getChildren()) {

      int tick = Integer.parseInt(subElement.getChild("Tick").getValue());
      String directionString = subElement.getChild("Direction").getValue();
      switch (directionString) {
        case "up":
          hashMap.put(tick, Direction.Up);
          break;
        case "right":
          hashMap.put(tick, Direction.Right);
          break;
        case "left":
          hashMap.put(tick, Direction.Left);
          break;
        case "down":
          hashMap.put(tick, Direction.Down);
          break;
        default:
          break;
      }
    }
  }

  /**
   * If there is a move to be done for the player, return the direction
   * $ @param tick Tick that connects to movement wanting to be played.
   */
  public Direction doPlayerMovement(int tick) {
    return this.replayedPlayerMovements.get(tick);
  }

  /**
   * If there is a move to be done for the actor, return the direction
   * $ @param tick Tick that connects to movement wanting to be played.
   */
  public Direction doActorMovement(int tick) {
    return this.replayedActorMovements.get(tick);
  }

  /**
   * Gets the next tick with a movement from parameter tick.
   * $ @param tick current tick
   * $ @param type String which contains "actor" or "player"
   */
  public Integer getNextMovementTick(int tick, String type) {
    if (type.equals("player")) {
      ArrayList<Integer> keyset = new ArrayList<>(replayedPlayerMovements.keySet());
      Collections.sort(keyset);
      for (Integer key : keyset) {
        if (key > tick) {
          return key;
        }
      }
    } else if (type.equals("actor")) {
      ArrayList<Integer> keyset = new ArrayList<>(replayedActorMovements.keySet());
      Collections.sort(keyset);
      for (Integer key : keyset) {
        if (key > tick) {
          return key;
        }
      }
    }
    return null;
  }

  /**
   * Updated current tick.
   *
   * @param tick current game tick
   */
  public void setTick(int tick) {
    this.tick = tick;
  }

  /**
   * Gets tick.
   */
  public int getTick() {
    return this.tick;
  }
}
