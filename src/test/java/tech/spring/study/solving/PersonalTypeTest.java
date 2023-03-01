package tech.spring.study.solving;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
public class PersonalTypeTest {

    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                Arguments.arguments(new String[] {"AN", "CF", "MJ", "RT", "NA"}, new int[] {5, 3, 2, 7, 5}, "TCMA"),
                Arguments.arguments(new String[] {"TR", "RT", "TR"}, new int[] {7, 1, 3}, "RCJA"),
                Arguments.arguments(new String[] {"TR", "RT", "AN", "NA", "MJ", "JM"}, new int[] {7, 7, 1, 1, 3, 3}, "RCJA")
        );
    }

    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(String[] survey, int[] choices, String expected) {
        String result = solution(survey, choices);
        Assertions.assertEquals(expected, result);
    }

    // 응답자는 어떤 질문에 대해 1~7번 답을 선택합니다. 질문은 예를 들면 AB 두 유형 중 어느 유형에 가까운지를 검사합니다.
    // 1~3번은 A에 가까운 정도를 나타내며, 5~7번은 B에 가까운 정도를 나타냅니다.
    // 1번으로 갈수록 A에 매우 가까운 정도이며, 7번으로 갈 수록 B에 매우 가까운 정도를 나타냅니다.
    // AB 두 유형이 획득한 점수가 동점인 경우 A,B중 알파벳 순서에 앞서는 유형이 선택됩니다.
    public String solution(String[] survey, int[] choices) {
        HashMap<String, Integer> scoreSheet = new HashMap<>(); // Key: 성격 유형, Value: 획득한 점수
        initSheet(scoreSheet);

        // 각 유형 별로 획득한 점수를 집계합니다.
        IntStream.range(0, survey.length).forEach(i -> {
            String[] twoIndicators = survey[i].split("");
            Optional<String> indicator = relatedIndicator(choices[i], twoIndicators);
            int score = mappedScore(choices[i]);

            if (indicator.isPresent()) {
                Integer sumOfScore = scoreSheet.get(indicator.get());
                sumOfScore += score;
                scoreSheet.put(indicator.get(), sumOfScore);
            }
        });

        // 집계한 데이터로부터 1~4번 지표의 성격 유형을 결정합니다.
        String[][] personalityTypes = { { "R", "T" }, { "C", "F" }, { "J", "M" }, { "A", "N" }};
        return Stream.of(personalityTypes)
                     .map(candidates -> selectPersonalityWithHighScore(candidates, scoreSheet))
                     .collect(Collectors.joining());
    }

    private void initSheet(HashMap<String,Integer> database) {
        database.put("R", 0);
        database.put("T", 0);
        database.put("C", 0);
        database.put("F", 0);
        database.put("J", 0);
        database.put("M", 0);
        database.put("A", 0);
        database.put("N", 0);
    }

    private Optional<String> relatedIndicator(int choice, String[] indicators) {
        if (choice >= 1 && choice <= 3) {
            return Optional.of(indicators[0]);
        } else if (choice >= 5 && choice <= 7) {
            return Optional.of(indicators[1]);
        } else {
            return Optional.empty();
        }
    }

    private int mappedScore(int choice) {
        int[] scores = { -1, 3, 2, 1, 0, 1, 2, 3 };
        return scores[choice];
    }

    private String selectPersonalityWithHighScore(String[] candidates, HashMap<String, Integer> scoreSheet) {
        Integer score0 = scoreSheet.get(candidates[0]);
        Integer score1 = scoreSheet.get(candidates[1]);

        if (score0 > score1) {
            return candidates[0];
        } else if (score0 < score1) {
            return candidates[1];
        } else {
            return getPrecedingAlphabet(candidates[0], candidates[1]);
        }
    }

    private String getPrecedingAlphabet(String str1, String str2) {
        return str1.compareTo(str2) > 0 ? str2 : str1;
    }
}
