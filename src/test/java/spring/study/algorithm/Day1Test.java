package spring.study.algorithm;

import org.junit.jupiter.api.Test;

/**
 * 1. 느낀점
 * 알고리즘 문제 해결 연습을 시작한 첫 날입니다. 간단한 문제 같았는데 코드로 작성하다 보니 쉽지 않습니다.
 *
 * 2. 개선방향
 * - 코드를 단계적으로 테스트 해나갈 필요가 있겠습니다.
 * - IDE의 자동완성 기능의 도움을 받을 수 없는 것이 익숙하지 않네요. 간단한 라이브러리 코드 타이핑이 익숙해 져야겠습니다
 *
 */
public class Day1Test {

    @Test
    void testSolution() {
        int result = solution("banana");
        System.out.println("result = " + result);
    }

    int solution(String input) {
        int divideCount=0;

        String subInput = input;
        while (!subInput.isEmpty()) {
            DivideResult divideResult = divide(subInput);
            divideCount++;
            subInput = divideResult.remained;
        }

        return divideCount;
    }

    class DivideResult {
        String divided;
        String remained;
    }

    DivideResult divide(String input) {
        char x = input.charAt(0);
        int xCount = 0;
        int otherCount = 0;
        for (int i =0; i<input.length(); i++ ){
            if (input.charAt(i) == x) {
                xCount ++;
            } else {
                otherCount ++;
            }

            if (xCount == otherCount) {
                DivideResult result = new DivideResult();
                result.divided = input.substring(0, i + 1); // 검증 필요
                result.remained = input.substring(i + 1);
                System.out.println("devide = " + result.divided);
                System.out.println("reamain = " + result.remained);
                return result;
            }
        }

        DivideResult result = new DivideResult();
        result.divided = input;
        result.remained = "";
        return result;
    }
}
