package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

/**
   * Recorder object saves, stores and exports all movements made by users and players.
   * $ @author Shae West, 300565911
 */
public class Recorder {
  private ArrayList<String> playerMovements;
  private ArrayList<String> enemyMovements;
  private GamePanel gamePanel;
  private int levelNumber;
  private HashMap<Integer, Integer> replayedPlayerMovements;
  private HashMap<Integer, Integer> replayedEnemyMovements;

  /**
   * Creates an Recorder,
   * If recordingName matches any current .XML files, load Recording from file.
   * Else, create a new empty Recorder object to be saved at a later date.
   * $ @param levelNumber Determines if enemy movements are saved && what file they are saved to.
   * $ @param gamePanel Game's gamePanel to access current tick. 
   */
  
  public Recorder(int levelNumber, GamePanel gamePanel) {
    this.playerMovements = new ArrayList<>();
    this.enemyMovements = new ArrayList<>();
    this.levelNumber = levelNumber;
    this.gamePanel = gamePanel;
  }

  /**
   * Adds a player movement to storage.
   * $ @param keyCode Integer value of keybind press 
   */
  public void savePlayerMovement(int keyCode) {
    Integer[] keyArray = new Integer[] {38, 40, 37, 39, 32, 27, 17, 88, 83, 82, 49, 50};
    List<Integer> validKeys = new ArrayList<Integer>(Arrays.asList(keyArray));
    if (!validKeys.contains(keyCode)) {
      return;
    }
    this.playerMovements.add(keyCode + ":" + this.gamePanel.getTick());
    this.saveToXml();
    loadToXml(1);
  }

  /**
   * Saves the current savedSnapshots to XML format.
   */
  public void saveToXml() {
    Element root = new Element("root");
    
    Element playerMovements = new Element("PlayerMovements");
    Element enemyMovements = new Element("EnemyMovements");

    for (String movement : this.playerMovements) {
      playerMovements.addContent(new Element("movement").setText(movement.toString()));
    }
    root.addContent(playerMovements);

    if (this.levelNumber == 2) {
      for (String movement : this.enemyMovements) {
        enemyMovements.addContent(new Element(movement.toString()));
      }
      root.addContent(enemyMovements);
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

    //TODO: Uncomment >> Resets stored movements
    //this.playerMovements = new ArrayList<>();
    //this.enemyMovements = new ArrayList<>();
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
    Element playerMovementsElement =  doc.getRootElement().getChild("PlayerMovements");
    for(Element element : playerMovementsElement.getChildren()){
      String[] split = element.getValue().split(":");
      int keycode = Integer.valueOf(split[0]);
      int tick = Integer.valueOf(split[1]);
      this.replayedPlayerMovements.put(tick, keycode);
    }

    if(level == 2){
      Element enemyMovementsElement = doc.getRootElement().getChild("EnemyMovements");
      for(Element element : enemyMovementsElement.getChildren()){
        String[] split = element.getValue().split(":");
        int keycode = Integer.valueOf(split[0]);
        int tick = Integer.valueOf(split[1]);
        this.replayedEnemyMovements.put(tick, keycode);
      }
    }
  }

}
