package edu.wpi.teamname;

import edu.wpi.teamname.db.Database;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");

    // Initialize database connection
//    try {
//      Database.init();
//    } catch (ClassNotFoundException e) {
//      throw new RuntimeException(e);
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    System.out.println("check");
    Parent root = FXMLLoader.load(getClass().getResource("views/ExampleFXML.fxml"));
    Scene scene = new Scene(root, 1280, 800);

    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");

    // Close database connection
//    try {
//      Database.closeConnection();
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
  }
}
