package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileWriter;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
   * Recorder object saves, stores and exports all movements made by users and players.
   * $ @author Shae West, 300565911
 */
public class Recorder {
  ArrayList<String> savedSnapshots;
  String recordingName;
  /**
   * Creates an Recorder,
   * If recordingName matches any current .XML files, load Recording from file.
   * Else, create a new empty Recorder object to be saved at a later date.
   * $ @param recordingName Name of XML file.
   */
  
  public Recorder(String recordingName) {
    //recordingName.toLowerCase();
    //TODO: Implement loading from file via string name
    if (recordingName.equals("ASavedFile"))  {
      this.loadFromXml(recordingName);
    } else  {
      savedSnapshots = new ArrayList<>();
    }
  }

  /**
   * Saves (all actions, or state each tick).
   * TODO: Decide what is passed as argument to save
   */
  public void saveMovement(String movementType) {
    //TODO: Probably changing to ENUM saving.
    savedSnapshots.add(movementType);
  }

  /**
   * Saves the current savedSnapshots to XML format.
   */
  public void saveToXml() {
    //create document
    Document document = new Document();
    Element root = new Element("root");
    Element moves = new Element("moves");
    for (String current : savedSnapshots) {
      moves.addContent(new Element("move").setText(current));
    }
    root.addContent(moves);

    document.setContent(root);

    try {
      FileWriter writer = new FileWriter("userinfo.xml");
      XMLOutputter outputter = new XMLOutputter();
      outputter.setFormat(Format.getPrettyFormat());
      outputter.output(document, writer);
      outputter.output(document, System.out);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  /**
   * Loads local .XML recording if it exists
   * $ @param recordingName Used to find local .XML saved recording
   */
  private void loadFromXml(String recordingName){
    //TODO
  }
}
