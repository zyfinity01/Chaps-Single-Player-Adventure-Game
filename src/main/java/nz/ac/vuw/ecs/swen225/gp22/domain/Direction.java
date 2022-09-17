package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The possible movement directions.
 * 
 * @author Jonty Morris, 300563915.
 */
public enum Direction {
  Up {
    public int getX() {
      return 0;
    }

    public int getY() {
      return -1;
    }
  },
  
  Down {
    public int getX() {
      return 0;
    }

    public int getY() {
      return 1;
    }
  },
  
  Left {
    public int getX() {
      return -1;
    }

    public int getY() {
      return 0;
    }
  },
  
  Right {
    public int getX() {
      return 1;
    }

    public int getY() {
      return 0;
    }
  };

  public abstract int getX();
  public abstract int getY();
}
