package code.test.test.mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

public class MockTest extends MockitoBaseTest {
    @Mock
    Collaborator collaborator;
    @Captor
    ArgumentCaptor<Message> argumentCaptor;

    @Test
    @DisplayName("어노테이션을 통해 목 객체를 생성합니다")
    void annotation_makes_mock_objects() {
        Assertions.assertNotNull(collaborator);
        Assertions.assertNotNull(argumentCaptor);
    }

    @Test
    @DisplayName("목 객체는 미리 정의된 특정 값을 반환합니다")
    void mock_returns_specific_predefined_value() {
        // Given
        int fixed = collaborator.supply();
        Assertions.assertEquals(fixed, collaborator.supply());
        when(collaborator.supply()).thenReturn(fixed + 10);

        // When
        int actual = collaborator.supply();

        // Then
        Assertions.assertEquals(fixed + 10, actual);
    }

    @Test
    @DisplayName("목 객체는 미리 정의된 특정 예외를 던집니다")
    void mock_throws_specific_predefined_exception() {
        // Given
        when(collaborator.supply()).thenThrow(RuntimeException.class);

        // Expected
        Assertions.assertThrows(RuntimeException.class, collaborator::supply);
    }

    @Test
    @DisplayName("목 객체는 람다식 처리 결과 값을 반환합니다")
    void mock_returns_value_through_lambda_expression() {
        // Given
        when(collaborator.supply()).thenAnswer(invocation -> {
            // Do something
            return 123;
        });

        // When
        int actual = collaborator.supply();

        // Then
        Assertions.assertEquals(123, actual);
    }

    @Test
    @DisplayName("목 객체는 다양한 Stub 패턴을 모사합니다")
    void mock_simulates_variety_stub_pattern() {
        // Given
        when(collaborator.supply())
                .thenThrow(RuntimeException.class)
                .thenReturn(10);

        // Expected
        Assertions.assertThrows(RuntimeException.class, collaborator::supply);
        Assertions.assertEquals(10, collaborator.supply());
    }

    @Test
    @DisplayName("목 객체는 입력된 메시지 타입을 캡처합니다")
    void mock_capture_output_type() {
        // When
        collaborator.consume(new Message("A", 1));

        // Then
        verify(collaborator, times(1)).consume(any(Message.class));
    }

    @Test
    @DisplayName("목 객체는 입력된 메시지를 캡처합니다")
    void mock_capture_output_message() {
        // When
        Message message = new Message("A", 1);
        collaborator.consume(message);

        // Then
        verify(collaborator, times(1)).consume(message);
    }

    @Test
    @DisplayName("목 객체는 입력된 메시지 목록을 캡처합니다")
    void mock_capture_output_message_list() {
        // When
        Message message1 = new Message("A", 1);
        Message message2 = new Message("B", 2);
        collaborator.consume(message1);
        collaborator.consume(message2);

        // Then
        verify(collaborator, times(2)).consume(argumentCaptor.capture());
        List<Message> allOutputs = argumentCaptor.getAllValues();
        Assertions.assertEquals(allOutputs.get(0), message1);
        Assertions.assertEquals(allOutputs.get(1), message2);
    }

    @Test
    @DisplayName("목 객체는 타임아웃 시간과 함꼐 입력 메시지를 검증합니다")
    void mock_verify_with_timeout() {
        // When
        Message message = new Message("A", 1);

        Executor delayedExecutor = CompletableFuture.delayedExecutor(1000, TimeUnit.MILLISECONDS);
        delayedExecutor.execute(() -> collaborator.consume(message));

        // Then
        verify(collaborator, timeout(10000).times(1)).consume(message);
    }
}
