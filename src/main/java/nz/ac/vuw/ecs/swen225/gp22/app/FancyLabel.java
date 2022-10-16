package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;

/**
* Label with the custom font. 
*
* @author Sam Redmond, 300443508
*/
class FancyLabel extends JLabel {
  
  /**
  * Create a new fancy label.
  *
  * @param text text to display
  */
  public FancyLabel(String text) {
    super(text);
    setForeground(Color.WHITE);
    try {
      setFont(Font.createFont(
          Font.TRUETYPE_FONT,
          new File("resources//fonts//BobFont.otf")
        ).deriveFont(15f)
      );
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
  }
  
}