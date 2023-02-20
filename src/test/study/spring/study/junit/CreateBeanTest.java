package spring.study.junit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
        SingletonBean.class,
        PrototypeBean.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("JUnit 테스트 클래스 내 싱글톤 빈은 한 번, 프로토타입 빈은 테스트 메서드 마다 생성됩니다.")
public class CreateBeanTest {
    @Autowired
    SingletonBean singletonBean;
    @Autowired
    PrototypeBean prototypeBean;

    @Test
    @Order(1)
    void test1() {
        assertThat(Statistics.singletonCount).isEqualTo(1);
        assertThat(Statistics.prototypeCount).isEqualTo(1);
    }

    @Test
    @Order(2)
    void test2() {
        assertThat(Statistics.singletonCount).isEqualTo(1);
        assertThat(Statistics.prototypeCount).isEqualTo(2);
    }
}
