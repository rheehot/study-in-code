package test.helper;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import test.data.ChangeCommand;
import test.data.Message;

/**
 * JUnit 오픈소스 프로젝트에 assertUntilTimeout 기능을 제한하기 위한 샘플 코드를 작성에 필요한 더미 의존 클래스를 정의합니다.
 */
public class FakeTarget {
    // volatile 키워드를 선언하지 않는 경우 sendCommand() 메서드에서 value 필드를 변경하더라도
    // 다른 쓰레드에서 readStatus() 메서드를 호출하면 변경된 값을 읽지 못할 수 있습니다.
    private volatile int value;
    private volatile Message message;

    public void connect() {
        value = 0;
        message = new Message("", 0);
    }

    public void send(ChangeCommand command, Duration delayMillis) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(delayMillis.toMillis());
                value = command.getValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void send(int value, Duration delayMillis) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(delayMillis.toMillis());
                this.value = value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void send(int value) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1);
                this.value = value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void send(Message message, Duration delayMillis) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(delayMillis.toMillis());
                this.message = message;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public int readStatus() {
        return value;
    }

    public Message readMessage() {
        return message;
    }
}
