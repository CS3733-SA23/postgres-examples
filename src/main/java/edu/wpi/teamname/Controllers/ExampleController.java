package edu.wpi.teamname.Controllers;

import java.io.IOException;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ExampleController {
  @FXML Button ClickButton; // fx:ID of the button in the ExampleFXML
  private Connection connection = null; // connection to database

  /** Method run when controller is initializes */
  public void initialize() {
    // if connection is successful
    if (this.connectToDB()) {
      this.createTable();
    }
  }

  /**
   * When the button is clicked, the method will log the data in the terminal and database
   *
   * @param actionEvent event that triggered method
   * @throws IOException
   */
  public void buttonClicked(ActionEvent actionEvent) throws IOException {
    System.out.println("Button was clicked");
    System.out.println(this.logData() ? "Data logged" : "Data NOT logged");
  }

  /**
   * Generates connection to server on localhost at default port (1521) be aware of the username and
   * password when testing
   *
   * @return True when connection is successful, False when failed
   */
  private boolean connectToDB() {

    try {
      // create Connection
      this.connection =
          DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Password1");
      if (this.connection != null) {
        System.out.println("Connected to the database!");
      } else {
        System.out.println("Failed to make connection!");
      }
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    // connection successful, return true
    return true;
  }

  /**
   * generates a table to store button click information
   *
   * @return true when table is successfully created or already exists, false otherwise
   */
  private boolean createTable() {

    boolean table_exists = false;

    if (this.connection != null) {
      String createQuery =
          "CREATE TABLE SYSTEM.buttonClicks("
              + "id NUMBER GENERATED BY DEFAULT AS IDENTITY, "
              + "btn_name VARCHAR(50), "
              + "time_stamp TIMESTAMP NOT NULL, "
              + "PRIMARY KEY(id) )";
      try {
        Statement statement = this.connection.createStatement();
        statement.execute(createQuery);

        table_exists = true;
      } catch (SQLException e) {
        // Error code 955 is "name is already used by an existing object", so this table name
        // already exists
        if (e.getErrorCode() == 955) table_exists = true;
        else e.printStackTrace();
      }
    }
    return table_exists;
  }

  /**
   * Stores button click data to database
   *
   * @return true if data is stored successfully, false otherwise
   */
  private boolean logData() {
    if (connection != null) {
      String writeQuery =
          "INSERT INTO SYSTEM.buttonClicks(btn_name, time_stamp) VALUES ( 'ClickButton', CURRENT_TIMESTAMP ) ";
      try {
        Statement statement = this.connection.createStatement();
        statement.execute(writeQuery);
        return true;

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}