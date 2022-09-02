package study.tobyspring1.service;

import org.springframework.transaction.annotation.Transactional;
import study.tobyspring1.domain.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void deleteAll();
    void update(User user);
    void upgradeLevels();
    User get(String id);
    List<User> getAll();
}
