package tech.javalang.runtime;

import org.junit.jupiter.api.Test;

public class RuntimeTest {
    /**
     * 현재 런타임 컴퓨터의 프로세서 개수(가상 쓰레드 포함)를 출력합니다.
     */
    @Test
    void availableProcessors() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
