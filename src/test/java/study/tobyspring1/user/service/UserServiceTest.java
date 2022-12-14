package study.tobyspring1.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import study.tobyspring1.AppContext;
import study.tobyspring1.TestAppContext;
import study.tobyspring1.user.dao.UserDao;
import study.tobyspring1.user.domain.Level;
import study.tobyspring1.user.domain.User;
import study.tobyspring1.user.service.UserService;
import study.tobyspring1.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static study.tobyspring1.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static study.tobyspring1.user.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Import(TestAppContext.class)
//@ContextConfiguration(classes = {AppContext.class, TestAppContext.class})
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    TestUserService testUserService;
    @Autowired
    UserDao userDao;
    @Autowired
    DefaultListableBeanFactory bf;

    @Test
    public void beans() {
        for (String n : bf.getBeanDefinitionNames()) {
            System.out.println(n + " \t " + bf.getBean(n).getClass().getName());
        }
    }

    List<User> users;

    @BeforeEach
    public void setUp() {
        users = Arrays.asList(
                new User("test1", "?????????1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "email1"),
                new User("test2", "?????????2", "password2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0, "email2"),
                new User("test3", "?????????3", "password3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD - 1, "email3"),
                new User("test4", "?????????4", "password4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD, "email4"),
                new User("test5", "?????????5", "password5", Level.GOLD, 100, Integer.MAX_VALUE, "email5")
        );
    }

    @Test
    public void bean() {
        assertThat(this.userService).isNotNull();
    }

    @Test
    public void add() {
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        System.out.println(userService);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        for (User user : users) {
            userDao.add(user);
        }

        try {
            this.testUserService.upgradeLevels();
//            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {
            System.out.println("upgradeAllOrNothing " + e);
        }

        userDao.getAll().stream().forEach(user -> System.out.println(user.toString()));
        checkLevelUpgraded(users.get(1), false);
    }

    @Test
    public void upgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size()).isEqualTo(2);
        checkUserAndLevel(updated.get(0), "test2", Level.SILVER);
        checkUserAndLevel(updated.get(1), "test4", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    @Test
    public void mockUpgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        UserDao mockUserDao = mock(UserDao.class);
        when(mockUserDao.getAll()).thenReturn(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MailSender mockMailSender = mock(MailSender.class);
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        verify(mockUserDao, times(2)).update(any(User.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArg.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
        assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
    }

//    @Test
//    public void advisorAutoProxyCreator() {
//        assertThat(testUserService).isOfAnyClassIn(java.lang.reflect.Proxy.class);
//    }

    @Test
    public void readOnlyTransactionAttribute() {
        testUserService.getAll();
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        }
        else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    public static class TestUserService extends UserServiceImpl {
        private String id = "test2";

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

        @Override
        public List<User> getAll() {
            for (User user: super.getAll()) {
                super.update(user);
                System.out.println("getAll " + user);
            }
            return null;
        }
    }

    static class TestUserServiceException extends RuntimeException {}

    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<String>();

        public List<String> getRequests() {
            return requests;
        }

        @Override
        public void send(SimpleMailMessage simpleMessage) throws MailException {
            requests.add(simpleMessage.getTo()[0]);
        }

        @Override
        public void send(SimpleMailMessage... simpleMessages) throws MailException {}
    }

    static class MockUserDao implements UserDao {
        private List<User> users;
        private List<User> updated = new ArrayList<>();

        private MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return updated;
        }

        public List<User> getAll() {
            return this.users;
        }

        public void update(User user) {
            updated.add(user);
        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }

    }
}