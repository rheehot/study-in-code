package spring.study.solving;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TemplarWeaponTest {
    @Test
    void tester() {
        int solution = solution(5, 3, 2);
        Assertions.assertEquals(10, solution);
    }

    public int solution(int number, int limit, int downward) {
        return IntStream.range(1, number + 1) // 1 - number 까지 약수의 개수를 구한다.
                        .map(this::toDivisorNumber) // 약수의 개수는 n번 기사가 구매하려는 무기의 공격력이 된다.
                        .map(power -> power > limit ? downward : power) // 만약 구매하려는 무기의 공격력이 limit를 초과하는 경우 downward 공격력을 가진 무기를 구매하도록 한다.
                        .sum(); // 모든 기사가 구매하려는 공격력을 다 더한다.
    }

    private int toDivisorNumber(int number) {
        int divisorNumber = 0;

        for (int i = 1; i * i <= number; i++) {
            if (i * i == number) {
                divisorNumber ++;
            } else if (number % i == 0) {
                divisorNumber +=2;
            }
        }
        return divisorNumber;
    }
}
