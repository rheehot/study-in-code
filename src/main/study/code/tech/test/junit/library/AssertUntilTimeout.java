package code.tech.test.junit.library;

import code.tech.test.junit.library.funtional.AssertionSupplier;
import org.junit.jupiter.api.AssertionFailureBuilder;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.platform.commons.util.ExceptionUtils.throwAsUncheckedException;

@SuppressWarnings({ "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass" })
public class AssertUntilTimeout {

    /**
     * 제한시간(timeout) 동안 특정 조건을 만족(assertOnce)하는지 반복 확인합니다. 조건 만족 시 즉시 작업을 종료하고, 타임아웃 시 테스트
     * 실패 컨텍스트를 출력하고 실패를 알리는 예외를 던집니다.
     *
     * @param timeout 제한시간
     * @param assertOnce 조건 확인 메서드
     * @param messageOnFailure 실패 메시지 메서드
     */
    public static void assertUntilTimeout(Duration timeout, AssertionSupplier assertOnce, Supplier<String> messageOnFailure) {
        if (!tryUntilTimeout(timeout, assertOnce)) {
            buildAndThrowAssertionFailure(timeout, messageOnFailure);
        }
    }

    private static boolean tryUntilTimeout(Duration timeout, AssertionSupplier workOnce) {
        long timeoutMillis = timeout.toMillis();
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < timeoutMillis) {
            try {
                if (workOnce.get()) {
                    return true;
                }
            } catch (Throwable ex) {
                throwAsUncheckedException(ex);
            }
        }
        return false;
    }

    private static void buildAndThrowAssertionFailure(Duration timeout, Supplier<String> messageOnFailure) {
        AssertionFailureBuilder.assertionFailure()
                               .message(messageOnFailure)
                               .reason("execution exceeded timeout of " + timeout.toMillis() + " ms")
                               .buildAndThrow();
    }

    private AssertUntilTimeout() {
        /* no-op */
    }
}