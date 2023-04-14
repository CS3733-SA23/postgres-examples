package edu.wpi.teamname.database.thing;

import edu.wpi.teamname.database.DataRepo;
import edu.wpi.teamname.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThingDAO {
    Database db;
    public ThingDAO(Database db) throws SQLException {
        this.db = db;
        if (!db.tableExists(Thing.getTableName())) {
            this.initTable();
        }
    }
    public void initTable() throws SQLException {
        String sql =
                String.join(
                        " ",
                        "CREATE TABLE thing",
                        "(id SERIAL PRIMARY KEY,",
                        "name VARCHAR(255),",
                        "data VARCHAR(255) );");
        db.processUpdate(sql);
    }

    public List<Thing> getAll() throws SQLException {
        ArrayList<Thing> things = new ArrayList<>();
        String sql = "SELECT * FROM thing;";
        ResultSet rs = db.processQuery(sql);
        while (rs.next()) {
            things.add(new Thing(rs.getString("name"), rs.getString("data"), rs.getInt("id")));
        }
        return things;
    }

    public Thing insert(Thing thing) throws SQLException {
        String sql = "INSERT INTO thing (name, data) VALUES (?,?);";
        PreparedStatement ps = db.prepareKeyGeneratingStatement(sql);
        ps.setString(1, thing.getName());
        ps.setString(2, thing.getData());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            thing.setId(rs.getInt(1));
        }
        return thing;
    }

    public void update(Thing thing) throws SQLException {
        String sql = "UPDATE thing SET name = ?, data = ? WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, thing.getName());
        ps.setString(2, thing.getData());
        ps.setInt(3, thing.getID());
        ps.executeUpdate();
    }

    public void delete(Thing thing) throws SQLException {
        String sql = "DELETE FROM thing WHERE id = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, thing.getID());
        ps.executeUpdate();
    }

}
