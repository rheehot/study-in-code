package tech.java.clazz.inheritance;

public class SubClass extends SuperClass {

    public SubClass() {
        System.out.println("SubClass() called");
    }

    public SubClass(int ignore) {
        System.out.println("SubClass(int ignore) called");
    }

    public SubClass(int member, int ignore) {
        // 주의! 오직 자식 클래스의 초기화 전(물리적으로 생성자 첫 줄)에서만 super 클래스 생성자 호출 가능
        super(member);
        System.out.println("SubClass(int member, int ignore) called");
    }
}
