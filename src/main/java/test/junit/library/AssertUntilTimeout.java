package test.junit.library;

import static org.junit.platform.commons.util.ExceptionUtils.throwAsUncheckedException;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.AssertionFailureBuilder;

import test.junit.library.funtional.AssertionSupplier;

@SuppressWarnings({ "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass" })
public class AssertUntilTimeout {

    /**
     * 제한시간(timeout) 동안 특정 조건을 만족(assertOnce)하는지 반복 확인합니다. 조건 만족 시 즉시 실행을 반환하고, 타임아웃 시 테스트
     * 실패를 알리는 예외를 던집니다.
     *
     * @param timeout 제한시간
     * @param assertOnce 한 번 조건을 확인하는 함수
     * @param messageSupplier 실패 메시지 생성 함수
     */
    public static void assertUntilTimeout(Duration timeout, AssertionSupplier assertOnce, Supplier<String> messageSupplier) {
        if (!tryUntilTimeout(timeout, assertOnce)) {
            buildAndThrowAssertionFailure(timeout, messageSupplier);
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

    private static void buildAndThrowAssertionFailure(Duration timeout, Supplier<String> messageSupplier) {
        AssertionFailureBuilder.assertionFailure()
                               .message(messageSupplier)
                               .reason("execution exceeded timeout of " + timeout.toMillis() + " ms")
                               .buildAndThrow();
    }

    private AssertUntilTimeout() {
        /* no-op */
    }
}