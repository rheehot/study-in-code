package code.tech.spring.study.solving;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class SmallSubStringTest {
    @Test
    void tester() {
        int solution = solution("500220839878", "7");
        Assertions.assertEquals(8, solution);
    }

    public int solution(String target, String sub) {
        assert sub.length() >= 1 && sub.length() <= 18;
        assert target.length() >= sub.length() && target.length() <= 10000;
        assert sub.charAt(0) != '0' && target.charAt(0) != '0';

        return (int) IntStream.range(0, target.length() - sub.length() + 1)
                              .mapToObj(startIndex -> target.substring(startIndex, startIndex + sub.length()))
                              .map(Long::parseLong)
                              .filter(number -> number <= Long.parseLong(sub))
                              .count();
    }
}
