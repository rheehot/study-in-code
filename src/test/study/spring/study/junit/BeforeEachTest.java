package spring.study.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

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
