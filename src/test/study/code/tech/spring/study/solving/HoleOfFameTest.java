package code.tech.spring.study.solving;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class HoleOfFameTest {
    @Test
    void tester() {
        int[] solution = solution(3, new int[] { 10, 100, 20, 150, 1, 100, 200 });
        Assertions.assertArrayEquals(new int[] { 10, 10, 10, 20, 20, 100, 100}, solution);
    }

    public int[] solution(int maxNum, int[] inputScores) {
        int[] dailyLastIn = new int[inputScores.length];

        for (int i = 1; i <= inputScores.length; i++) {
            dailyLastIn[i-1] = daySolution(maxNum, inputScores, i);
        }

        return dailyLastIn;
    }

    @Test
    void daySolutionTest() {
        int[] dailyScores = { 10, 100, 20, 150, 1, 100, 200 };
        int bottom = daySolution(3, dailyScores, 4);
        Assertions.assertEquals(20, bottom);

        bottom = daySolution(5, dailyScores, 4);
        Assertions.assertEquals(10, bottom);
    }

    public int daySolution(int rankLimitTo, int[] dailyScores, int dayTo) {
        // 내림차순 정렬
        SortedMap<Integer, Integer> sortedMap = new TreeMap<>(Comparator.reverseOrder());

        // 오늘까지 점수를 모두 정렬한다.
        for (int day = 1; day <= dayTo; day++) {
            int score = dailyScores[day - 1];
            if (sortedMap.containsKey(score)) {
                sortedMap.put(score, sortedMap.get(score) + 1);
            } else {
                sortedMap.put(score, 1);
            }
        }

        // 제한 수에 들어오면서 가장 낮은 점수를 찾는다.
        int rankingPerson = 0;
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            int sameScorePerson = entry.getValue();
            rankingPerson += sameScorePerson;
            if (rankingPerson >= rankLimitTo) {
                return entry.getKey();
            }
        }

        // 전체가 제한 수에 들어가는 경우 마지막에 있는 가장 낮은 점수를 반환한다.
        return sortedMap.lastKey();
    }
}
