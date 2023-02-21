package code.tech.spring.study.functional;

public class Person {
    public String hello(Introduce supplier) {
        return "Hello! " + supplier.apply();
    }
}
