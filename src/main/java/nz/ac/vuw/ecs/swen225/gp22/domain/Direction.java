package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The possible movement directions.
 *
 * @author Jonty Morris, 300563915.
 */
public enum Direction {
  /**
   * Up direction.
   */
  Up {
    public int getX() {
      return 0;
    }

    public int getY() {
      return -1;
    }

    @Override
    public Direction opposite() {
      return Direction.Down;
    }
  },
  
  /**
   * Down direction.
   */
  Down {
    public int getX() {
      return 0;
    }

    public int getY() {
      return 1;
    }

    @Override
    public Direction opposite() {
      return Direction.Up;
    }
  },
  
  /**
   * Left direction.
   */
  Left {
    public int getX() {
      return -1;
    }

    public int getY() {
      return 0;
    }

    @Override
    public Direction opposite() {
      return Direction.Right;
    }
  },
  
  /**
   * Right direction.
   */
  Right {
    public int getX() {
      return 1;
    }

    public int getY() {
      return 0;
    }

    @Override
    public Direction opposite() {
      return Direction.Left;
    }
  };

  /**
   * Get the direction x offset.
   *
   * @return x offset.
   */
  public abstract int getX();
  
  /**
   * Get the direction y offset.
   *
   * @return y offset.
   */
  public abstract int getY();

  /**
   * Get the opposite direction.
   *
   * @return opposite direction.
   */
  public abstract Direction opposite();
}
