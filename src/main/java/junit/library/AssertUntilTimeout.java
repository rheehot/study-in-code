package junit.library;

import static org.junit.platform.commons.util.ExceptionUtils.throwAsUncheckedException;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.AssertionFailureBuilder;
import org.junit.jupiter.api.function.ThrowingSupplier;

@SuppressWarnings({ "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass" })
public class AssertUntilTimeout {
    /**
     * Asserts that the supplied {@code assertOnce} returns {@code true} within the
     * given {@code timeout}.
     *
     * @param timeout the maximum time in milliseconds the code under test is allowed to run
     * @param assertOnce the code under test
     * @throws AssertionError if the code under test throws an exception or returns {@code false}
     */
    public static void assertUntilTimeout(Duration timeout, ThrowingSupplier<Boolean> assertOnce, Supplier<String> messageSupplier) {
        if (!tryUntilTimeout(timeout, assertOnce)) {
            buildAndThrowAssertionFailure(timeout, messageSupplier);
        }
    }

    /**
     * Try that the supplied {@code assertOnce} returns {@code true} within the
     * given {@code timeout}.
     *
     * @param timeout the maximum time in milliseconds the code under test is allowed to run
     * @param assertOnce the code under test
     * @throws AssertionError if the code under test throws an exception or returns {@code false}
     */
    private static boolean tryUntilTimeout(Duration timeout, ThrowingSupplier<Boolean> assertOnce) {
        long timeoutMillis = timeout.toMillis();
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < timeoutMillis) {
            try {
                if (assertOnce.get()) {
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