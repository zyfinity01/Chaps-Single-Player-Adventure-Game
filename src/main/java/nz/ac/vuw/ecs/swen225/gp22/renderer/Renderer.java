package nz.ac.vuw.ecs.swen225.gp22.renderer;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Renderer {
    // TODO: rescale/crop to fit
    private static int windowWidth = 20;
    private static int tileWidth = 20;

    public static void render (Maze maze, Graphics2D image){

        for (int x=0; x<maze.getCols(); x++) {
            for (int y=0; y<maze.getRows(); y++) {
                image.drawImage(image(maze.getTiles()[y][x])).image(), null, x*tileWidth, y*tileWidth);
            }
        }
    }

    // TODO: Find tileset
    public static BufferedImage image(Tile tile) throws IOException {
        // TODO: It would be good to cache the images for performance and to avoid IOExceptions mid game
//        // can't switch on instanceof
//        if (tile instanceof Wall) { return }
//        if (tile instanceof Key) { return }
//        if (tile instanceof Door) { return }
//        if (tile instanceof Lock) { return }
//        if (tile instanceof Info) { return }
//        if (tile instanceof Treasure) { return }
//        if (tile instanceof Exit) { return }
//        if (tile instanceof Chap) {
//            // TODO: switch on direction
//            switch (tile.direction) {
//                case UP: return
//                case DOWN: return
//                case LEFT: return
//                case RIGHT: return
//            }
//        }
        return ImageIO.read(new File("images//free.png"));
    }
}

