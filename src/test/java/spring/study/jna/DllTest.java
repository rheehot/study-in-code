package spring.study.jna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.jna.TestLibBridge;

import static org.assertj.core.api.Assertions.assertThat;


public class DllTest {
    TestLibBridge bridge;

    @BeforeEach
    void setUp() {
        bridge = new TestLibBridge();
        bridge.load();
    }

    @Test
    void hello() {
        assertThat(bridge.hello()).isEqualTo("Hello Dll");
    }

    @Test
    void loadTwice() {
        bridge.load();
        bridge.load();
        assertThat(bridge.getInt()).isEqualTo(1);
    }
}
