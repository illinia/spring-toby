package study.tobyspring1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.tobyspring1.dao.UserDao;
import study.tobyspring1.domain.Level;
import study.tobyspring1.domain.User;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final UserLevelUpgradePolicy userLevelUpgradePolicy;


    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
            }
        }

//        userDao.getAll().stream().forEach(user -> {
//            Boolean changed = null;
//
//            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//                user.setLevel(Level.SILVER);
//                changed = true;
//            }
//            else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//                user.setLevel(Level.GOLD);
//                changed = true;
//            }
//            else if (user.getLevel() == Level.GOLD) {changed = false;}
//            else {changed = false;}
//
//            if (changed) {userDao.update(user);}
//        });

    }



    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
