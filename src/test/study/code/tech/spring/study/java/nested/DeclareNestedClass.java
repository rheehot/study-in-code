package code.tech.spring.study.java.nested;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeclareNestedClass {

    @Test
    @DisplayName("Inner Class는 자기를 정의한 클래스 내에서 사용가능합니다.")
    void declareTest() {
        InnerClass innerClass = new InnerClass();
        Assertions.assertEquals("InnerClass", innerClass.getName());
    }

    @Getter
    public class InnerClass {
        public String name = "InnerClass";
    }

    @Getter
    public static class StaticInnerClass {
        public String name = "StaticInnerClass";
    }
}
