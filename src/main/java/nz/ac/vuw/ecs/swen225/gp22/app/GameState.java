package nz.ac.vuw.ecs.swen225.gp22.app;

import static java.util.Map.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores game state and runs handler on updates.
 */
public class GameState {

  private StateUpdateHandler handler;
  private Map<String, Object> values;

  GameState(StateUpdateHandler handler) {
    this.handler = handler;
    values = new HashMap<>(Map.ofEntries(
      entry("timeLeft", (Object) 1.00),
      entry("chipsLeft", (Object) 5),
      entry("isPaused", (Object) false),
      entry("inventory", (Object) List.of())
    ));
  }

  public Object get(String field) {
    return values.get(field);
  }
  
  public void set(String field, Object value) {
    values.put(field, value);
    handler.handle(field, value);
  }
    
}