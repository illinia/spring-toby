package study.tobyspring1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {

    public Connection makeNewConnection() throws SQLException {
        Connection c = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/tobyspring", "sa", "");
        return c;
    }
}
