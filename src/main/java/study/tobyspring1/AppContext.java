package study.tobyspring1;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import study.tobyspring1.user.dao.SqlConfiguration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "study.tobyspring1.user")
@Import({SqlConfiguration.class, ProductionAppContext.class})
@PropertySource("classpath:/database.properties")
public class AppContext {

    @Value("${db.url}") String url;
    @Value("${db.username}") String username;
    @Value("${db.password}") String password;

    @Autowired
    Environment env;

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

//        dataSource.setJdbcUrl("jdbc:h2:mem:testdb;MODE=MySQL");
//        dataSource.setJdbcUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setJdbcUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        return dataSource;
    }
}
