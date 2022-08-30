package study.tobyspring1.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import study.tobyspring1.domain.Level;
import study.tobyspring1.domain.User;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserDaoTest {

    @Autowired
    private UserDaoJdbc dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        this.user1 = new User("test1", "테스트1", "spring1", Level.BASIC, 1, 0, "email1");
        this.user2 = new User("test2", "테스트2", "spring2", Level.SILVER, 55, 10,"email2");
        this.user3 = new User("test3", "테스트3", "spring3", Level.GOLD, 100, 40,"email2");

        System.out.println(this);
    }

    @Test
    public void addAndGet() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userGet1 = dao.get(user1.getId());
        checkSameUser(userGet1, user1);

        User userGet2 = dao.get(user2.getId());
        checkSameUser(userGet2, user2);
    }

    @Test
    public void count() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);

    }

    @Test
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        assertThatThrownBy(() -> dao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size()).isEqualTo(0);

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    @Test
    public void duplicateKey() {
        dao.deleteAll();

        dao.add(user1);
//        dao.add(user1);
        assertThatThrownBy(() -> dao.add(user1))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("수정테스트");
        user1.setPassword("password");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        dao.update(user1);

        User user1update = dao.get(user1.getId());
        checkSameUser(user1, user1update);
        User user2same = dao.get(user2.getId());
        checkSameUser(user2, user2same);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}