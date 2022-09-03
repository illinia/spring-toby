package study.tobyspring1.user.dao;

public class UserDaoConnectionCountingTest {
//
//    @Test
//    @Transactional
//    public void test() throws SQLException {
//
//        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
//
//        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
//
//        User user = new User();
//        user.setId("id");
//        user.setName("테스트");
//        user.setPassword("password");
//
//        dao.add(user);
//
//        System.out.println(user.getId() + " 등록 성공");
//
//        User user2 = dao.get(user.getId());
//
//        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
//        System.out.println("Connection counter : " + ccm.getCounter());
//    }
}
