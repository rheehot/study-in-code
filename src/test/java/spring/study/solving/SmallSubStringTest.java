package spring.study.solving;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmallSubStringTest {
    @Test
    void tester() {
        int solution = solution("500220839878", "7");
        Assertions.assertEquals(8, solution);
    }

    public int solution(String target, String sub) {
        return (int) IntStream.range(0, target.length() - sub.length() + 1)
                              .mapToObj(startIndex -> target.substring(startIndex, startIndex + sub.length()))
                              .map(Long::parseLong)
                              .filter(number -> number <= Long.parseLong(sub))
                              .count();
    }
}
