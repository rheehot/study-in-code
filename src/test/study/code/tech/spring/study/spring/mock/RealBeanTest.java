package code.tech.spring.study.spring.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SampleBean.class})
public class RealBeanTest {
    @Autowired
    SampleBean realBean;

    @Test
    @DisplayName("Real Bean 테스트")
    void realBeanHello() {
        Assertions.assertEquals("hello", realBean.hello());
    }
}