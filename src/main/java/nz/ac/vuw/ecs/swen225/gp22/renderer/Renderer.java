package nz.ac.vuw.ecs.swen225.gp22.renderer;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import java.awt.*;

interface Renderer { void render (Maze Maze, Graphics graphics); }

/**
 * @author Lawrence Schwabe (at date of file creation), 300570719.
 * 
 */
class RendererImpl implements Renderer{
  
    public void render(Maze maze, Graphics graphics) {

        for (int x=0; x<maze.getCols(); x++) {
            for (int y=0; y<maze.getRows(); y++) {

            }
        }
    }


}
