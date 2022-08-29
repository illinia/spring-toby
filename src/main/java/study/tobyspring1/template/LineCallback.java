package study.tobyspring1.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
