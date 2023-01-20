package edu.wpi.teamname.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
  private static Connection connection = null;

  public static void init() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    connection =
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres", "postgres", "password");

    if (!tableExists("things")) {
      createTables();
    }
  }

  public static void createTables() throws SQLException {
    connection.createStatement().executeUpdate("CREATE TABLE things(name VARCHAR);");
  }

  public static void addThing(String name) throws SQLException {
    String queryTemplate = "INSERT INTO things(name) VALUES(?);";
    PreparedStatement stmt = connection.prepareStatement(queryTemplate);
    stmt.setString(1, name);
    stmt.executeUpdate();
  }

  public static List<String> getThings() throws SQLException {
    List<String> things = new ArrayList<String>();

    if (connection != null) {
      ResultSet rs2 = connection.createStatement().executeQuery("SELECT * FROM things;");
      while (rs2.next()) {
        things.add(rs2.getString(1));
      }
    }

    return things;
  }

  public static void closeConnection() throws SQLException {
    connection.close();
  }

  private static boolean tableExists(String tableName) throws SQLException {
    String query =
        "SELECT EXISTS (\n"
            + "SELECT FROM\n"
            + "   pg_tables\n"
            + "WHERE\n"
            + "   schemaname = 'public' AND\n"
            + "   tablename  = ?\n"
            + ");\n";

    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setString(1, tableName);
    ResultSet rs = stmt.executeQuery();
    rs.next();

    return rs.getBoolean(1);
  }
}
