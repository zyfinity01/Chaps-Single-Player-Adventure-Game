package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * State of the maze.
 *
 * @author Jonty Morris, 300563915.
 */
public enum State {
    /** 
     * Game is still running.
     */
    Running,
    /**
     * Chap is out of time.
     */
    OutOfTime,
    /**
     * Level is complete. 
     */
    Complete,
    /**
     * Chap is dead.
     */
    Dead
}
