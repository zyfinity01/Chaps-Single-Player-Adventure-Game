package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.awt.event.KeyEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

import nz.ac.vuw.ecs.swen225.gp22.app.GamePanel;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;

/**
 * Recorder object saves, stores and exports all movements made by users and players.
 * $ @author Shae West, 300565911
 */
public class Recorder{
  private ArrayList<String> playerMovements;
  private ArrayList<String> actorMovements;
  private GamePanel gamePanel;
  private int levelNumber;
  private HashMap<Integer, Direction> replayedPlayerMovements;
  private HashMap<Integer, Direction> replayedActorMovements;

  /**
   * Creates an Recorder,
   * If recordingName matches any current .XML files, load Recording from file.
   * Else, create a new empty Recorder object to be saved at a later date.
   * $ @param levelNumber Determines if actor movements are saved && what file they are saved to.
   * $ @param gamePanel Game's gamePanel to access current tick. 
   */
  
  public Recorder(int levelNumber, GamePanel gamePanel) {
    this.playerMovements = new ArrayList<>();
    this.actorMovements = new ArrayList<>();
    this.levelNumber = levelNumber;
    this.gamePanel = gamePanel;
  }

  /**
   * Adds a player movement to storage.
   * $ @param keyCode Integer value of keybind press 
   */
  public void savePlayerMovement(int keyCode) {
    Integer[] keyArray = new Integer[] {KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN};
    List<Integer> validKeys = new ArrayList<Integer>(Arrays.asList(keyArray));
    if (!validKeys.contains(keyCode)) {
      return;
    }
    String directionString = "empty";
    switch(keyCode){
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
    }
    this.playerMovements.add(this.gamePanel.getTick() + ":" + directionString);
    this.saveToXml();
  }

  /**
   * Saves the current savedSnapshots to XML format.
   */
  public void saveToXml() {
    Element root = new Element("root");
    
    Element playerMovements = new Element("PlayerMovements");
    Element actorMovements = new Element("ActorMovements");

    for (String movement : this.playerMovements) {
      playerMovements.addContent(new Element("movement").setText(movement.toString()));
    }
    root.addContent(playerMovements);

    if (this.levelNumber == 2) {
      for (String movement : this.actorMovements) {
        actorMovements.addContent(new Element(movement.toString()));
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
   * Loads all movements based off level number
   * 
   * $ @param level Determines which moves' file to load
   */
  private void loadToXml(int level){
    Document doc = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = factory.newDocumentBuilder();
      org.w3c.dom.Document w3cDocument = documentBuilder.parse("moves_level_" + level + ".xml");
      doc = new DOMBuilder().build(w3cDocument);
    } catch (IOException | SAXException | ParserConfigurationException e) {
      e.printStackTrace();
    }
    if(level == 1){
      this.replayedPlayerMovements = new HashMap<Integer, Direction>();
      Element playerMovementsElement =  doc.getRootElement().getChild("PlayerMovements");
      saveMovesToHashMap(playerMovementsElement, this.replayedPlayerMovements);
    } else if (level == 2){
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
  private void saveMovesToHashMap(Element element, HashMap<Integer, Direction> hashMap){
    for(Element subElement : element.getChildren()){
      String[] split = subElement.getValue().split(":");
      int tick = Integer.valueOf(split[1]);
      switch(split[0]){
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
      }
    }
  }

  /**
   * If there is a move to be done for the player, return the keycode
   * 
   * $ @param tick Tick that connects to movement wanting to be played.
   */
  public Direction doPlayerMovement(int tick){
    return this.replayedPlayerMovements.get(tick);
  }

  /**
   * If there is a move to be done for the actor, return the keycode
   * 
   * $ @param tick Tick that connects to movement wanting to be played.
   */
  public Direction doActorMovement(int tick){
    return this.replayedActorMovements.get(tick);
  }
}
