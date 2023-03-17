package tech.java.clazz.inner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InnerClassTest {
    /**
     * 내부 클래스는 외부 클래스 인스턴스에 종속됩니다.
     * 그래서 내부 클래스는 외부 클래스 인스턴스 없이는 생성할 수 없습니다.
     */
    @Test
    void innerClass() {
        OuterClass myOuter = new OuterClass();
        //OuterClass.InnerClass myInner = new OuterClass.InnerClass(); // compile error
        OuterClass.InnerClass myInner = myOuter.new InnerClass(); // need an outer object to create an inner object
        assertEquals(30, myInner.myInnerMethod());
    }

    /**
     * 정적 내부 클래스는 외부 클래스와 독립적으로 인스턴스를 생성할 수 있습니다.
     * 그래서 정적 내부 클래스는 외부 클래스의 정적 멤버에만 접근 가능합니다.
     */
    @Test
    void staticInnerClass() {
        OuterClass.StaticInnerClass myStaticInner = new OuterClass.StaticInnerClass(); // no need for an outer object
        assertEquals(20, myStaticInner.myStaticInnerMethod());
    }
}
