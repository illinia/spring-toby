package study.tobyspring1.proxyClass;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.*;

class HelloTest {

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("Toby")).isEqualTo("Hello Toby");
        assertThat(hello.sayHi("Toby")).isEqualTo("Hi Toby");
        assertThat(hello.sayThankYou("Toby")).isEqualTo("Thank You Toby");

        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertThat(proxiedHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxiedHello.sayThankYou("TOBY")).isEqualTo("THANK YOU TOBY");

        Hello proxiedHello2 = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        assertThat(proxiedHello2.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello2.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxiedHello2.sayThankYou("TOBY")).isEqualTo("THANK YOU TOBY");
    }

}