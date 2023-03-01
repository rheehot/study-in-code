package tech.test.helper;

import tech.test.data.ChangeInventory;
import tech.test.data.Message;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * JUnit 오픈소스 프로젝트에 assertUntilTimeout 기능을 제한하기 위한 샘플 코드를 작성에 필요한 더미 의존 클래스를 정의합니다.
 */
public class FakeStore {
    // volatile 키워드를 선언하지 않는 경우 sendCommand() 메서드에서 value 필드를 변경하더라도
    // 다른 쓰레드에서 readStatus() 메서드를 호출하면 변경된 값을 읽지 못할 수 있습니다.
    private volatile int value;
    private volatile Message message;
    private Duration asyncDelay; // 비동기 동작 모의를 위해 요청을 받고, 일정 시간 지연 후 작업을 처리합니다.

    public void init(Duration asyncDelay) {
        this.asyncDelay = asyncDelay;
        value = 0;
        message = new Message("", 0);
    }

    public void requestSync(ChangeInventory command) {
        value = command.getInventory();
    }

    public void requestAsync(ChangeInventory command) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(asyncDelay.toMillis());
                value = command.getInventory();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void requestAsync(int value) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(asyncDelay.toMillis());
                this.value = value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void requestAsync(Message message) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(asyncDelay.toMillis());
                this.message = message;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public int getInventory() {
        return value;
    }

    public Message readMessage() {
        return message;
    }
}
