package spring.study.java;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SwitchStatementTest {

    @Test
    void switchEnumTest() {
        Assertions.assertEquals("ONE", switchEnum(SampleEnum.ONE));
    }

    // 아래 switch 문은 컴파일 되지 않습니다.
//    String switchString(String input) {
//        return switch (input) {
//            case "Hello" -> "Hello";
//            case "Hi" -> "Hi";
//        };
//    }

    String switchEnum(SampleEnum arg) {
        // SampleEnym에 THREE라는 값을 추가하면 아래 switch 문에 컴파일 오류가 발생합니다.
        return switch (arg) {
            case ONE -> "ONE";
            case TWO -> "TWO";
        };
    }

    @RequiredArgsConstructor
    @Getter
    private enum SampleEnum {
        ONE(1), TWO(2);
        private final int num;
    }
}
