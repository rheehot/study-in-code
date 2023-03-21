package tech.java.clazz.inheritance;

public class SuperClass {
    private final int member;

    public SuperClass() {
        member = 0;
        System.out.println("SuperClass() called");
    }

    public SuperClass(int member) {
        this.member = member;
        System.out.println("SuperClass(int member) called");
    }
}
