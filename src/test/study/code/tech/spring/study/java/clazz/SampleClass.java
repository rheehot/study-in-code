package code.tech.spring.study.java.clazz;

public class SampleClass implements SampleInterface{
    @Override
    public boolean isTrue(String input) {
        return input.equals("true");
    }
}
