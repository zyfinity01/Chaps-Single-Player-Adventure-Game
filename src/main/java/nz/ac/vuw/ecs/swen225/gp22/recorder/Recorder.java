package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
  ArrayList<Integer> savedSnapshots;
  /**
   * Creates an Recorder,
   * If recordingName matches any current .XML files, load Recording from file.
   * Else, create a new empty Recorder object to be saved at a later date.
   * $ @param recordingName Name of XML file.
   */
  
  public Recorder() {
    savedSnapshots = new ArrayList<>();
  }

  /**
   * Saves (all actions, or state each tick).
   */
  public void saveMovement(int keyCode) {
    savedSnapshots.add(keyCode);
    this.saveToXml();
  }

  /**
   * Saves the current savedSnapshots to XML format.
   */
  public void saveToXml() {
    Document document = new Document();
    Element root = new Element("root");
    Element moves = new Element("moves");
    for (Integer current : savedSnapshots) {
      moves.addContent(new Element("move").setText(current.toString()));
    }
    root.addContent(moves);

    document.setContent(root);

    try {
      FileOutputStream outstream = new FileOutputStream("moves.xml");
      OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
      XMLOutputter outputter = new XMLOutputter();
      outputter.setFormat(Format.getPrettyFormat());
      outputter.output(document, writer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
