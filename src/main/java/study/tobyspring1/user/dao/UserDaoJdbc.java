package study.tobyspring1.user.dao;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import study.tobyspring1.user.domain.Level;
import study.tobyspring1.user.domain.User;
import study.tobyspring1.user.sqlservice.SqlService;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SqlService sqlService;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };


    public void add(final User user) {
//        try {
        this.jdbcTemplate.update(
//                    this.sqlMap.get("add"),
                this.sqlService.getSql("userAdd"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
//        } catch (DuplicateKeyException e) {
//            throw new DuplicateUserIdException(e);
//        }
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(
//                this.sqlMap.get("get"),
                this.sqlService.getSql("userGet"),
                new Object[]{id}, this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(
//                this.sqlMap.get("getAll")
                this.sqlService.getSql("userGetAll")
                , this.userMapper);
    }


    public void deleteAll() {
        this.jdbcTemplate.update(
//                this.sqlMap.get("deleteAll")
                this.sqlService.getSql("userDeleteAll")
        );
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(
//                this.sqlMap.get("getCount")
                this.sqlService.getSql("userGetCount")
                , Integer.class);
    }

    @Override
    public void update(User user) {
        System.out.println("Dao update");
        this.jdbcTemplate.update(
//                this.sqlMap.get("update"),
                this.sqlService.getSql("userUpdate"),
                user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId()
        );
    }


}

