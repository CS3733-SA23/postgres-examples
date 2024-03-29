package edu.wpi.teamname.database;

import edu.wpi.teamname.database.thing.Thing;
import edu.wpi.teamname.database.thing.ThingDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRepo {

  private static DataRepo instance;
  private final ThingDAO thingDAO;
  private final Database db;

  public static void init(String host, String database_name, int port, String username, String password) throws SQLException, ClassNotFoundException {
    instance = new DataRepo(host, database_name, port, username, password);
  }

  public DataRepo(String host, String database_name, int port, String username, String password) throws ClassNotFoundException, SQLException {
    db = new Database(host, database_name, port, username, password);
    // init DAOs
    thingDAO = new ThingDAO(db);
  }

  public static DataRepo getInstance() {
    return instance;
  }

  // Things
  public List<Thing> getAllThings() throws SQLException {
    return thingDAO.getAll();
  }

  public Thing insertThing(Thing thing) throws SQLException {
    return thingDAO.insert(thing);
  }

  public void updateThing(Thing thing) throws SQLException {
    thingDAO.update(thing);
  }

  public void deleteThing(Thing thing) throws SQLException {
    thingDAO.delete(thing);
  }

  public void close() throws SQLException {
    db.closeConnection();
  }
}
