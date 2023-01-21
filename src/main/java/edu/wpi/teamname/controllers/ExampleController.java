package edu.wpi.teamname.controllers;

import edu.wpi.teamname.database.thing.Thing;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ExampleController {
  @FXML Button insertButton;
  @FXML Button queryButton;
  @FXML VBox thingList;

  /** Method run when controller is initialized */
  public void initialize() {
    insertButton.setOnAction((actionEvent) -> addData());
    queryButton.setOnAction((actionEvent) -> getData());
  }

  /** Inserts thing into database */
  private void addData() {
    Thing thing = new Thing("test", "test");
    try {
      thing.insert();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /** Queries data from database, displays in list */
  private void getData() {
    List<Thing> things;

    try {
      things = Thing.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    thingList.getChildren().clear();
    for (Thing thing : things) {
      Label thingLabel = new Label(String.format("Thing %d", thing.getID()));
      thingList.getChildren().add(thingLabel);
    }
  }
}
