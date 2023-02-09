package junit.library;

import static org.junit.platform.commons.util.ExceptionUtils.throwAsUncheckedException;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.AssertionFailureBuilder;
import org.junit.jupiter.api.function.ThrowingSupplier;

@SuppressWarnings({ "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass" })
public class AssertUntilTimeout {

    private AssertUntilTimeout() {
        /* no-op */
    }

    /**
     * Asserts that the supplied {@code assertOnce} returns {@code true} within the
     * given {@code timeout}.
     *
     * @param timeout the maximum time in milliseconds the code under test is allowed to run
     * @param assertOnce the code under test
     * @throws AssertionError if the code under test throws an exception or returns {@code false}
     */
    public static void assertUntilTimeout(Duration timeout, ThrowingSupplier<Boolean> assertOnce, Supplier<String> messageSupplier) {
        long timeoutMillis = timeout.toMillis();
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < timeoutMillis) {
            try {
                if (assertOnce.get()) {
                    return;
                }
            } catch (Throwable ex) {
                throwAsUncheckedException(ex);
            }
        }

        AssertionFailureBuilder.assertionFailure()
                               .message(messageSupplier)
                               .reason("execution exceeded timeout of " + timeoutMillis + " ms")
                               .buildAndThrow();
    }

}