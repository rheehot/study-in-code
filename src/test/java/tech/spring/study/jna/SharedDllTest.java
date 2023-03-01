package tech.spring.study.jna;

import code.tech.spring.jna.TestLibBridge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(OrderAnnotation.class)
public class SharedDllTest {
    TestLibBridge bridge;

    @BeforeEach
    void setUp() {
        bridge = new TestLibBridge();
    }

    @Test
    void hello() {
        bridge.load();
        assertThat(bridge.hello()).isEqualTo("Hello Dll");
    }

    @Test
    @Order(1)
    void loadFirst() {
        bridge.load();
        assertThat(bridge.getInt()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void loadSecond() {
        bridge.load();
        assertThat(bridge.getInt()).isEqualTo(2);
    }
}
