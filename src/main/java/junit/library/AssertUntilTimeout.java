package junit.library;

import static org.junit.platform.commons.util.ExceptionUtils.throwAsUncheckedException;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.AssertionFailureBuilder;

import junit.library.funtional.ThrowingBooleanSupplier;

@SuppressWarnings({ "ClassWithOnlyPrivateConstructors", "NonFinalUtilityClass" })
public class AssertUntilTimeout {

    public static void work(Duration timeout, ThrowingBooleanSupplier assertOnce, Supplier<String> messageSupplier) {
        if (!tryUntilTimeout(timeout, assertOnce)) {
            buildAndThrowAssertionFailure(timeout, messageSupplier);
        }
    }

    private static boolean tryUntilTimeout(Duration timeout, ThrowingBooleanSupplier workOnce) {
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