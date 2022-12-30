package spring.study.solving;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmallSubStringTest {
    @Test
    void tester() {
        int solution = solution("500220839878", "7");
        Assertions.assertEquals(8, solution);
    }

    public int solution(String target, String sub) {
        assert target.length() >= sub.length();
        int subLen = sub.length();
        long subNumber = Long.parseLong(sub);
        List<Long> subNumbers = toSubNumbers(target, subLen);
        return (int) subNumbers.stream()
                               .filter(number -> number <= subNumber)
                               .count();
    }

    private List<Long> toSubNumbers(String target, int subLen) {
        List<Long> numbers = new ArrayList<>();

        for (int start = 0; start + subLen <= target.length(); start++) {
            Long number = Long.parseLong(target.substring(start, start + subLen));
            numbers.add(number);
        }
        return numbers;
    }
}
