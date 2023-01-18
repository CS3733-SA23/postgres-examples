package edu.wpi.teamname;

import java.sql.*;

public class Main {

  public static void main(String[] args) {
    //    App.launch(App.class, args);
    Connection connection = null;

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      connection =
          DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/postgres", "postgres", "password");

      connection
          .createStatement()
          .executeUpdate("CREATE TABLE stuff(name VARCHAR, value VARCHAR);");

      connection
          .createStatement()
          .executeUpdate("INSERT INTO stuff(name, value) VALUES('thing1', '42');");

      ResultSet rs2 = connection.createStatement().executeQuery("SELECT * FROM stuff;");
      while (rs2.next()) {
        System.out.print(rs2.getString(1));
        System.out.print(" --- ");
        System.out.println(rs2.getString(2));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
