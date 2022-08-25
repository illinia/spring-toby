package study.tobyspring1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DUserDao extends UserDao {
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }
}
