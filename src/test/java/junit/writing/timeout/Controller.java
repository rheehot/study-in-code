package junit.writing.timeout;

/**
 * JUnit 오픈소스 프로젝트에 assertUntilTimeout 기능을 제한하기 위한 샘플 코드를 작성에 필요한 더미 의존 클래스를 정의합니다.
 */
class Controller {
    int value;

    void connect() {
        value = 0;
    }

    void sendCommand(int value) {
        this.value = value;
    }

    int readStatus() {
        return value;
    }
}
