package study.tobyspring1.dao;

import org.springframework.transaction.annotation.Transactional;
import study.tobyspring1.domain.User;

import java.sql.*;

public class UserDao {

//    private SimpleConnectionMaker simpleConnectionMaker;
    private ConnectionMaker connectionMaker;

//    public UserDao(SimpleConnectionMaker simpleConnectionMaker) {
//        this.simpleConnectionMaker = simpleConnectionMaker;
//    }


    public UserDao() {
        connectionMaker = new DConnectionMaker();
    }

    @Transactional
    public static void main(String[] args) throws SQLException {
        UserDao dao = new UserDao();

        User user = new User();
        user.setId("id");
        user.setName("테스트");
        user.setPassword("password");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId());
    }

    public void add(User user) throws SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

//    public abstract Connection getConnection() throws SQLException;

//    private Connection getConnection() throws SQLException {
//        Connection c = DriverManager.getConnection(
//                "jdbc:h2:tcp://localhost/~/tobyspring", "sa", "");
//        return c;
//    }
}

