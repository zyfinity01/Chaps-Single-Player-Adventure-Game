package nz.ac.vuw.ecs.swen225.gp22.recorder;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author Shae West, 300565911
 */
public class Recorder {
  ArrayList savedSnapshots;
  String recordingName;
  /**
   * Creates an Recorder,
   * If recordingName matches any current .XML files, load Recording from file.
   * Else, create a new empty Recorder object to be saved at a later date.
   * @param recordingName Name of XML file.
   */
  public Recorder(String recordingName){
    recordingName.toLowerCase();
  }

  /**
   * Saves (all actions, or state each tick) 
   * TODO: Decide what is passed as argument to save
   */
  public void saveSnapshot(){

  }

  /**
   * Saves the current savedSnapshots to XML format
   */
  public void saveToXML(){

  }

  /**
   * Loads local .XML recording if it exists
   * @param recordingName Used to find local .XML saved recording
   */
  private void loadFromXML(String recordingName){

  }
}
