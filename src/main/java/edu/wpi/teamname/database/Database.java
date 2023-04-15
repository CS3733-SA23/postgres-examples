package edu.wpi.teamname.database;

import java.sql.*;

public class Database {
    Connection connection;

    public Database(String host, String database_name, int port, String username, String password) throws ClassNotFoundException, SQLException {
        String con_string = String.format("jdbc:postgresql://%s:%d/%s", host, port, database_name);
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(con_string, username, password);
    }

    public PreparedStatement prepareStatement(String s) throws SQLException {
        return connection.prepareStatement(s);
    }

    public PreparedStatement prepareKeyGeneratingStatement(String s) throws SQLException {
        return connection.prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
    }

    public ResultSet processQuery(String s) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(s);
        return rs;
    }

    public int processUpdate(String s) throws SQLException {
        Statement stmt = connection.createStatement();
        int numUpdated = stmt.executeUpdate(s);
        return numUpdated;
    }

    public void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }
    public boolean tableExists(String tableName) throws SQLException {
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
