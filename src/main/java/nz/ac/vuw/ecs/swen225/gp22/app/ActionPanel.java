package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
* Panel for action buttons.
*
* @author Sam Redmond, 300443508
*/
public class ActionPanel extends JPanel {
  
  ActionPanel() {
    super();
    setOpaque(false);
    setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
  }

  protected JButton createButton(String imagePath) {
    JButton button = null;
    try {
      var buttonIcon = ImageIO.read(new File(imagePath));
      button = new JButton(new ImageIcon(buttonIcon));
      button.setMargin(new Insets(0, 0, 0, 0));
      button.setOpaque(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return button;
  }
}
