package edu.wpi.teamname.database.thing;

import edu.wpi.teamname.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; // import java.util.stream.Stream;

public class Thing {
  private static final String tableName = "thing";
  private String name;
  private String data;
  private Integer id;

  public Thing(String name, String data) {
    this.name = name;
    this.data = data;
    this.id = null; // automatically set on insert
  }

  public Thing(String name, String data, Integer id) {
    this.name = name;
    this.data = data;
    this.id = id;
  }

  public static void initTable() throws SQLException {
    String sql =
        String.join(
            " ",
            "CREATE TABLE thing",
            "(id SERIAL PRIMARY KEY,",
            "name VARCHAR(255),",
            "data VARCHAR(255) );");
    Database.processUpdate(sql);
  }

  public static List<Thing> getAll() throws SQLException {
    ArrayList<Thing> things = new ArrayList<>();
    String sql = "SELECT * FROM thing;";
    ResultSet rs = Database.processQuery(sql);
    while (rs.next()) {
      things.add(new Thing(rs.getString("name"), rs.getString("data"), rs.getInt("id")));
    }
    return things;
  }

  public void insert() throws SQLException {
    String sql = "INSERT INTO thing (name, data) VALUES (?,?);";
    PreparedStatement ps = Database.prepareKeyGeneratingStatement(sql);
    ps.setString(1, name);
    ps.setString(2, data);
    ps.executeUpdate();
    ResultSet rs = ps.getGeneratedKeys();
    if (rs.next()) {
      id = rs.getInt(1);
    }
  }

  public void update() throws SQLException {
    String sql = "UPDATE thing SET name = ?, data = ? WHERE id = ?";
    PreparedStatement ps = Database.prepareStatement(sql);
    ps.setString(1, name);
    ps.setString(2, data);
    ps.setInt(3, id);
    ps.executeUpdate();
  }

  public void delete() throws SQLException {
    String sql = "DELETE FROM thing WHERE id = ?";
    PreparedStatement ps = Database.prepareStatement(sql);
    ps.setInt(1, id);
    ps.executeUpdate();
  }

  public static String getTableName() {
    return tableName;
  }

  public Integer getID() {
    return id;
  }
}
