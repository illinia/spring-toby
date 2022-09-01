package study.tobyspring1.service;

import org.springframework.transaction.annotation.Transactional;
import study.tobyspring1.domain.User;

import java.util.List;

//@Transactional
public interface UserService {
    void add(User user);
    void deleteAll();
    void update(User user);
    void upgradeLevels();
//    @Transactional(readOnly = true)
    User get(String id);
//    @Transactional(readOnly = true)
    List<User> getAll();
}
