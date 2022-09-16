package nz.ac.vuw.ecs.swen225.gp22.renderer;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

enum TileImage {
    // TODO: Find tileset
    // Load images in advance, to save time
    // TODO: this is kinda messy
    Wall {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Empty {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Key {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Door {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Info {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Treasure {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Lock {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Exit {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }},
    Chap {{ try { img = ImageIO.read(new File("images/wall")); } catch (IOException ignored) { } }};

    BufferedImage img;
    public BufferedImage image() { return img; }
}

public class Renderer {
    int windowWidth = 20;
    int tileWidth = 20;

    void render (Maze maze, Canvas canvas){
        Graphics2D image = new BufferedImage(windowWidth*tileWidth, windowWidth*tileWidth, BufferedImage.TYPE_4BYTE_ABGR).createGraphics();

        for (int x=0; x<maze.getCols(); x++) {
            for (int y=0; y<maze.getRows(); y++) {
                image.drawImage(TileImage.valueOf(maze.getTiles()[x][y].type()).image(), null, x*tileWidth, y*tileWidth);
            }
        }

        // TODO: I'll probably need to rescale this
        canvas.paint(image);
    }
}

