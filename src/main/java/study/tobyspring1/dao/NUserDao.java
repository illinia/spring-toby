package study.tobyspring1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NUserDao extends UserDao {
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }
}
