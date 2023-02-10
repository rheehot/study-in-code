package junit.writing.timeout;

import java.util.concurrent.CompletableFuture;

/**
 * JUnit 오픈소스 프로젝트에 assertUntilTimeout 기능을 제한하기 위한 샘플 코드를 작성에 필요한 더미 의존 클래스를 정의합니다.
 */
class FakeController {
    // volatile 키워드를 선언하지 않는 경우 sendCommand() 메서드에서 value 필드를 변경하더라도
    // 다른 쓰레드에서 readStatus() 메서드를 호출하면 변경된 값을 읽지 못할 수 있습니다.
    volatile int value;

    void connect() {
        value = 0;
    }

    void sendCommand(int value) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
                this.value = value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    int readStatus() {
        return value;
    }
}
