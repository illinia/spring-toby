package study.tobyspring1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class JUnitTest {

    @Autowired
    ApplicationContext context;

//    static JUnitTest testObject;
//
//    @Test public void test1() {
//        assertThat(this).isNotSameAs(testObject);
//    }
//
//    @Test public void test2() {
//        assertThat(this).isNotSameAs(testObject);
//        testObject = this;
//    }
//
//    @Test public void test3() {
//        assertThat(this).isNotSameAs(testObject);
//        testObject = this;
//    }

    static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
    static ApplicationContext contextObject = null;

    @Test public void test1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test public void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }

    @Test public void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context).isTrue();
        contextObject = this.context;
    }
}
