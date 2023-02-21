package code.tech.spring.study.string;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateStringTest {
    @Test
    void createStringFromChar() {
        char ch = 'a';
        String result = String.valueOf(ch);
        Assertions.assertThat(result).isEqualTo("a");
    }
}
