package tech.spring.study.java.reflection;

public class SampleClassImpl implements SampleInterface {
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public String hi(String name) {
        return "Hi " + name;
    }

    @SuppressWarnings("MethodMayBeStatic")
    private String privateMethod(String name) {
        return "Private " + name;
    }
}
