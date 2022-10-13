package spring.study.java.reflection;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReflectionTest {

    @Test
    void invokeMethod() throws Exception {
        String name = "Spring";

        assertThat(name.length()).isEqualTo(6);

        Method lengthMethod = String.class.getMethod("length");
        assertThat(lengthMethod.invoke(name)).isEqualTo(6);

        assertThat(name.charAt(0)).isEqualTo('S');

        // 리플렉션으로 메서드를 찾을 때는 파랄미터 타입을 모두 지정해야 한다.
        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat(charAtMethod.invoke(name, 0)).isEqualTo('S');
    }

    @Test
    @DisplayName("인터페이스로 부터 획득한 Method 만으로 구현 클래스의 실제 메서드를 호출할 수 있다")
    void interfaceMethodCallImplMethod() throws Exception {
        Method helloMethod = SampleInterface.class.getMethod("hello", String.class);
        SampleClassImpl implObject = new SampleClassImpl();
        String ret = (String) helloMethod.invoke(implObject, "Joo");
        assertThat(ret).isEqualTo("Hello Joo");
    }
}
