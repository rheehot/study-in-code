package spring.study.java.nested;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NestedClassTest {

    @Test
    @DisplayName("Inner Class 는 자신을 정의한 클래스 외부에서는 사용할 수 없습니다.")
    void declareInnerClassNotAllowed() {
//        DeclareClass.InnerClass innerClass = new DeclareClass.InnerClass(); // 컴파일 오류
//        Assertions.assertEquals("InnerClass", innerClass.getName());
    }

    @Test
    @DisplayName("Static Inner Class 는 자신을 정의한 클래스 외부에서도 자유롭게 사용할 수 있습니다.")
    void declareStaticInnerClassAllowed() {
        DeclareNestedClass.StaticInnerClass staticInnerClass = new DeclareNestedClass.StaticInnerClass();
        Assertions.assertEquals("StaticInnerClass", staticInnerClass.getName());
    }
}
