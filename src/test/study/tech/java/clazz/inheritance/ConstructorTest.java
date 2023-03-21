package tech.java.clazz.inheritance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConstructorTest {
    @Test
    @DisplayName("디폴트 생성자 호출 순서")
    void defaultConstructorOrder() {
        new SubClass();

        /* Output:
        SuperClass() called
        SubClass() called
         */
    }

    /**
     * 하위 클래스의 커스텀 생성자에서 부모 클래스의 커스텀 생성자를 명시적으로 호출하지 않는 경우 디폴트 생성자가 호출됩니다.
     */
    @Test
    @DisplayName("상위 클래스 디폴트 생성자의 암묵적 호출")
    void implicitCallSuperDefaultConstructor() {
        new SubClass(1);

        /* Output:
        SuperClass() called
        SubClass(int member) called
         */
    }

    /**
     * 하위 클래스의 커스텀 생성자에서 부모 클래스의 커스텀 생성자를 명시적으로 호출할 수 있습니다.
     * 단, 하위 클래스 생성 진행 전(물리적으로 생성자 첫 줄)에만 super 클래스를 호출할 수 있습니다.
     */
    @Test
    @DisplayName("상위 클래스 커스텀 생성자 명시적 호출")
    void directCallSuperCustomConstructor() {
        new SubClass(1, 2);

        /* Output:
        SuperClass(int member) called
        SubClass(int member, int ignore) called
         */
    }
}
