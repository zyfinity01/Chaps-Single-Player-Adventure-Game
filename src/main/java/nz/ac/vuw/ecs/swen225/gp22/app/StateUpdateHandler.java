package nz.ac.vuw.ecs.swen225.gp22.app;

interface StateUpdateHandler {
  void handle(String field, Object updatedValue);
}