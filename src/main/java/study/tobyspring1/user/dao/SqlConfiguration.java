package study.tobyspring1.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.tobyspring1.user.sqlservice.SimpleSqlService;
import study.tobyspring1.user.sqlservice.SqlService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SqlConfiguration {

    @Bean
    public SqlService sqlService() {
        Map<String, String> map = new HashMap<>();
        map.put("userAdd", "insert into users(id, name, password, level, login, recommend, email) values(?,?,?,?,?,?,?)");
        map.put("userGet", "select * from users where id = ?");
        map.put("userDeleteAll", "delete from users");
        map.put("userGetCount", "select count(*) from users");
        map.put("userUpdate", "update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ?");
        map.put("userGetAll", "select * from users order by id");

        return new SimpleSqlService(map);
    }
}
