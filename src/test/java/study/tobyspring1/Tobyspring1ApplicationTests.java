package study.tobyspring1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import study.tobyspring1.user.domain.Level;
import study.tobyspring1.user.domain.User;
import study.tobyspring1.user.service.UserService;

import static study.tobyspring1.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;

@SpringBootTest
class Tobyspring1ApplicationTests {

//	@Autowired
//	UserService userService;
//	@Autowired
//	ApplicationContext context;
//
//	@Test
//	@Transactional
//	public void Test() {
//		userService.deleteAll();
//		User user = new User("test1", "테스트1", "password1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0, "email1");
//		userService.add(user);
//	}
}
