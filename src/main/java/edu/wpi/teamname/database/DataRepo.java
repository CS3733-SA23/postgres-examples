package edu.wpi.teamname.database;

import edu.wpi.teamname.database.thing.Thing;
import edu.wpi.teamname.database.thing.ThingDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRepo {

  private static DataRepo instance;
  private Database db;
  private ThingDAO thingDAO;

  public static void init() throws SQLException, ClassNotFoundException {
    instance = new DataRepo();
  }

  public DataRepo() throws ClassNotFoundException, SQLException {
    db = new Database();
    // init DAOs
    init_daos();
  }

  public static DataRepo getInstance() {
    return instance;
  }

  public void init_daos() throws SQLException {
    thingDAO = new ThingDAO(db);
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
