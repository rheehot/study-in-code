package solving.twentythree.feb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FruitSellerTest {
    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(Arguments.arguments(3, 4, new int[] { 1, 2, 3, 1, 2, 3, 1 }, 8),
                         Arguments.arguments(4, 3, new int[] { 4, 1, 2, 2, 4, 4, 4, 4, 1, 2, 4, 2 }, 33));
    }

    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(int maxScore, int numberInBox, int[] scores, int expected) {
        int result = solution(maxScore, numberInBox, scores);
        Assertions.assertEquals(expected, result);
    }

    /**
     * 과일 장수가 박스에 사과를 담을 때 얻을 수 있는 최대 점수를 반환합니다. 과일 장수가 획득할 수 있는 점수는 박스 개수 x 한 상자에 담긴 사과의 개수 x 박스 안에서 최저 사과 점수입니다.
     * @param maxScore 사과 점수 최고점
     * @param numOfApplePerBox 한 상자에 담을 수 있는 박스 개수
     * @param appleScores 사과의 점수 배열
     * @return 과일 장수가 박스에 사과를 담을 때 얻을 수 있는 최대 점수
     */
    @SuppressWarnings("unused")
    public int solution(int maxScore, int numOfApplePerBox, int[] appleScores) {
        // 1. 사과의 점수를 기준으로 내림차순 정렬합니다.
        int[] sortedAppleScores = Arrays.stream(appleScores)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();

        // 2. 점수가 높은 사과부터 차례로 박스에 담습니다.
        List<List<Integer>> boxes = IntStream.range(0, appleScores.length / numOfApplePerBox)
                .sorted()
                .mapToObj(i -> Arrays.stream(sortedAppleScores, i * numOfApplePerBox, (i + 1) * numOfApplePerBox)
                        .boxed()
                        .toList())
                .toList();

        // 3. 전체 박스의 점수를 계산하고 합산합니다.
        return boxes.stream()
                .mapToInt(box -> box.size() * box.get(box.size() - 1))
                .sum();
    }
}
