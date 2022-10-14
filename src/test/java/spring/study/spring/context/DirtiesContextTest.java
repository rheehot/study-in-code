package spring.study.spring.context;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 다음 사이트를 보고 학습합니다. https://www.baeldung.com/spring-dirtiescontext
 */
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserCache.class)
public class DirtiesContextTest {
    @Autowired
    protected UserCache userCache;

    @Test
    @Order(1)
    void addJooAndPrintCache() {
        userCache.addUser("Joo");
        userCache.printUserList("addJooAndPrintCache");
    }

    @Test
    @Order(2)
    void printCache() {
        userCache.printUserList("printCache");
    }

    /**
     * Reference - https://www.baeldung.com/spring-dirtiescontext
     *
     * 5.1. Class Level
     * The ClassMode options for a test class define when the context is reset:
     *
     * BEFORE_CLASS: Before current test class
     * BEFORE_EACH_TEST_METHOD: Before each test method in the current test class
     * AFTER_EACH_TEST_METHOD: After each test method in the current test class
     * AFTER_CLASS: After the current test class
     *
     * 5.2. Method Level
     * The MethodMode options for an individual method define when the context is reset:
     *
     * BEFORE_METHOD: Before the current test method
     * AFTER_METHOD: After the current test method
     */
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    @Test
    @Order(3)
    void addYangAndPrintCache() {
        userCache.addUser("Yang");
        userCache.printUserList("addYangAndPrintCache");
    }

    @Test
    @Order(4)
    void printCacheAgain() {
        userCache.printUserList("printCacheAgain");
    }
}
