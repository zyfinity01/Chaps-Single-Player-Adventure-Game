package nz.ac.vuw.ecs.swen225.gp22.renderer;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Renderer {

    // TODO: rescale/crop to fit
    static int windowWidth = 20;
    static int tileWidth = 20;

    // Cached tiles
    // TODO: Find tileset
    static BufferedImage free;
    static BufferedImage wall;
    static BufferedImage key;
    static BufferedImage door;
    static BufferedImage lock;
    static BufferedImage info;
    static BufferedImage treasure;
    static BufferedImage exit;
    static BufferedImage chap;
    // load images
    static { try {
        free = ImageIO.read(new File("images//free.png"));
        wall = ImageIO.read(new File("images//wall.png"));
        chap = ImageIO.read(new File("images//chap.png"));
    } catch (IOException e) { e.printStackTrace(); }} // TODO: handle better

    public static BufferedImage image(Tile tile) {
        // can't switch on instanceof
        if (tile instanceof Wall) { return wall; }
        if (tile instanceof Key) { return key; }
        if (tile instanceof Door) { return door; }
        if (tile instanceof Lock) { return lock; }
        if (tile instanceof Info) { return info; }
        if (tile instanceof Treasure) { return treasure; }
        if (tile instanceof Exit) { return exit; }
        if (tile instanceof Chap) {
            // TODO: switch on direction
            return chap;
        }
        return free;
    }
    public static void render (Maze maze, Graphics2D image){

        for (int x=0; x<maze.getCols(); x++) {
            for (int y=0; y<maze.getRows(); y++) {
                image.drawImage(image(maze.getTiles()[y][x]), null, x*tileWidth, y*tileWidth);
            }
        }
    }
}

