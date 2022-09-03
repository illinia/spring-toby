package study.tobyspring1.user.service;

import study.tobyspring1.user.domain.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void deleteAll();
    void update(User user);
    void upgradeLevels();
    User get(String id);
    List<User> getAll();
}
