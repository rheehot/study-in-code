package tech.spring.study.spring.mock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Slf4j
@SpringBootTest(classes = {SampleBean.class})
@DisplayName("Mock Bean 테스트")
public class MockingBeanTest {
    @MockBean
    SampleBean mockBean;

    @Test
    @DisplayName("기본적으로 null 입력을 모의한다")
    void defaultMockReturnNull() {
        Assertions.assertNull(mockBean.hello());
    }

    @Test
    @DisplayName("특정 입력을 모의한다")
    void inputFromMock() {
        Mockito.when(mockBean.hello()).thenReturn("Hi");
        Assertions.assertEquals("Hi", mockBean.hello());
    }
}
