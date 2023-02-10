package junit.library;

import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TryUntilTimeout {
    public static boolean work(Supplier<Boolean> tryOnce, long timeoutMillis, Supplier<String> messageOnFailure) {
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis < timeoutMillis) {
            if (tryOnce.get()) {
                return true;
            }
        }
        log.info(messageOnFailure.get());
        return false;
    }

    private TryUntilTimeout() {}
}