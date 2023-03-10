package solving.twentythree.feb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
    @SuppressWarnings({"Convert2MethodRef", "SimplifyStreamApiCallChains"})
    public int solution(int maxScore, int numOfApplePerBox, int[] appleScores) {
        // 1. 사과의 점수를 기준으로 내림차순 정렬합니다.
        Integer[] sortedAppleScores = Arrays.stream(appleScores)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toArray(Integer[]::new);

        // 2. 점수가 높은 사과부터 차례로 박스에 담습니다.
        List<List<Integer>> boxes = IntStream.range(0, appleScores.length / numOfApplePerBox)
                .sorted()
                .mapToObj(boxIndex -> packAppleBox(boxIndex, numOfApplePerBox, sortedAppleScores))
                .collect(Collectors.toList());

        // 3. 전체 박스의 점수를 계산하고 합산합니다.
        return boxes.stream()
                .mapToInt(box -> calculateBoxScore(box))
                .sum();
    }

    /**
     * 사과의 점수를 기준으로 내림차순 정렬된 사과 점수 배열에서 박스에 담을 사과 점수 목록을 반환합니다.
     * @param boxIndex 박스 인덱스
     * @param numOfApplePerBox 한 상자에 담을 수 있는 박스 개수
     * @param sortedAppleScores 사과의 점수 배열
     * @return 박스에 담을 사과 점수 목록
     */
    private static List<Integer> packAppleBox(int boxIndex, int numOfApplePerBox, Integer[] sortedAppleScores) {
        return Arrays.stream(sortedAppleScores,
                        boxIndex * numOfApplePerBox,
                        (boxIndex + 1) * numOfApplePerBox)
                .collect(Collectors.toList());
    }

    /**
     * 박스에 담긴 사과의 점수 목록을 받아서 사과의 점수를 계산합니다. (사과 개수 x 박스 안에서 최저 사과 점수)
     * @param appleScores 박스에 담긴 사과의 점수 목록
     * @return 박스에 담긴 사과의 점수
     */
    private static int calculateBoxScore(List<Integer> appleScores) {
        // appleScores 정렬되어 있어야 한다는 숨겨진 제약이 있는데 개선 방법이 없을까?
        return appleScores.size() * getMinScore(appleScores);
    }

    /**
     * 박스에 담긴 사과의 점수 목록에서 최저 점수를 반환합니다. (박스에 담긴 사과의 점수 목록은 내림차순 정렬되어 있어야 합니다.)
     * @param box 박스에 담긴 사과의 점수 목록
     * @return 박스에 담긴 사과의 최저 점수
     */
    private static int getMinScore(List<Integer> box) {
        return box.get(box.size() - 1);
    }
}
