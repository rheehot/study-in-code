package tech.spring.study.java.clazz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ClazzTest {
    @Test
    @DisplayName("Object 타입으로 선언된 변수가 특정 인터페이스를 구현했다면 instanceof를 사용해 확인할 수 있습니다.")
    void instanceOfInterfaceTest() {
        Object sample = new SampleClass();
        Assertions.assertTrue(sample instanceof SampleInterface);
    }
}
