package edu.wpi.teamname;

import edu.wpi.teamname.database.DataRepo;
import java.io.IOException;
import java.sql.SQLException;

import edu.wpi.teamname.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  @Override
  public void init() throws SQLException, ClassNotFoundException {
    log.info("Starting Up");

    DataRepo.init();
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

    try {
      DataRepo.getInstance().close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
