package spring.study.junit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class BeforeEachTest {
    @BeforeEach
    void setUp() {
        log.error("Hello error message");
    }

    @Test
    void testMessage() {
        log.info("Hello test message");
    }
}
