package tech.javalang.functional;

public class Person {
    public String hello(Introduce supplier) {
        return "Hello! " + supplier.apply();
    }
}
