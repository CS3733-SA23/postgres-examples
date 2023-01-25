package edu.wpi.teamname.database;

import edu.wpi.teamname.database.thing.Thing;
import java.sql.*;

public class Database {
  private Connection connection;
  private static Database db;

  static {
    try {
      db = new Database();
    } catch (ClassNotFoundException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Database() throws ClassNotFoundException, SQLException {
    Class.forName("org.postgresql.Driver");
    connection =
        DriverManager.getConnection(
            "jdbc:postgresql://HOST:PORT/DATABASE", "USERNAME", "PASSWORD");
  }

  public static void init() throws SQLException {
    if (!tableExists(Thing.getTableName())) {
      Thing.initTable();
    }
  }

  public static PreparedStatement prepareStatement(String s) throws SQLException {
    return getInstance().connection.prepareStatement(s);
  }

  public static PreparedStatement prepareKeyGeneratingStatement(String s) throws SQLException {
    return getInstance().connection.prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
  }

  public static ResultSet processQuery(String s) throws SQLException {
    Statement stmt = getInstance().connection.createStatement();
    ResultSet rs = stmt.executeQuery(s);
    return rs;
  }

  public static int processUpdate(String s) throws SQLException {
    Statement stmt = getInstance().connection.createStatement();
    int numUpdated = stmt.executeUpdate(s);
    return numUpdated;
  }

  public static void closeConnection() throws SQLException {
    getInstance().connection.close();
    db = null;
  }

  public static boolean tableExists(String tableName) throws SQLException {
    String query =
        "SELECT EXISTS (\n"
            + "SELECT FROM\n"
            + "   pg_tables\n"
            + "WHERE\n"
            + "   schemaname = 'public' AND\n"
            + "   tablename  = ?\n"
            + ");\n";

    PreparedStatement stmt = getInstance().connection.prepareStatement(query);
    stmt.setString(1, tableName);
    ResultSet rs = stmt.executeQuery();
    rs.next();

    return rs.getBoolean(1);
  }

  public static Database getInstance() {
    return db;
  }
}
