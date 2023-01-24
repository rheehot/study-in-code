package spring.study.solving;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PersonalTypeTest {

    @Test
    void testSolution() {
        String result = solution(new String[]{"TR", "RT", "TR"},
                              new int[]{7, 1, 3});
        Assertions.assertEquals("RCJA", result);
    }

    enum ChoiceType {
        POSITIVE, NONE, NEGATIVE
    }

    /**
     * N개의 질문 각각이 조사하는 두 가지 성격 유형을 문자열 배열로 주어집니다. 그리고 그에 대한 설문자의 답변이 1~7번까지 주어집니다.
     * 이 때 질문자의 성격 유형(유형에는 번호가 부여되어 있음)을 번호 순서대로 결정하여 반환합니다. 한 지표에서 두 가지 성격 유형이
     * 동점인 경우에는 사전 순으로 빠른 유형을 선택합니다.
     */
    public String solution(String[] survey, int[] choices) {
        HashMap<Character, Integer> database = new HashMap<>();

        // 각 질문지가 조사하는 두 성격 유형에 대한 조사자의 답변 데이터를 수집합니다.
        IntStream.range(0, survey.length)
                .forEach(i -> {
                    int choiceScore = choices[i];
                    ChoiceType choiceType = matchChoiceType(choiceScore);
                    String twoSurveyType = survey[i];
                    Optional<Character> matchedType = matchPersonalType(twoSurveyType, choiceType);
                    int score = matchChoiceScore(choiceScore);
                    matchedType.ifPresent( type -> {
                        Integer prevScore = database.get(matchedType.get());
                        if (prevScore == null) {
                            prevScore = 0;
                        }
                        database.put(matchedType.get(), prevScore + score);
                    });
                });

        // 데이터로부터 1~4번 지표의 성격 유형을 판단합니다.
        StringBuilder answer = new StringBuilder();
        Stream.of("RT", "CF", "JM", "AN")
              .map(twoTypeAlphabetic -> toSelectedType(database, twoTypeAlphabetic))
              .forEach(answer::append);

        return answer.toString();
    }

    private Character toSelectedType(HashMap<Character, Integer> database, String twoTypeAlphabetic){
        Integer firstScore = database.get(twoTypeAlphabetic.charAt(0));
        Integer secondScore = database.get(twoTypeAlphabetic.charAt(1));
        if (firstScore == null && secondScore == null) {
            return twoTypeAlphabetic.charAt(0);
        }
        if (firstScore == null) {
            return twoTypeAlphabetic.charAt(1);
        }
        if (secondScore == null) {
            return twoTypeAlphabetic.charAt(0);
        }
        if (firstScore >= secondScore) {
            return twoTypeAlphabetic.charAt(0);
        } else {
            return twoTypeAlphabetic.charAt(1);
        }
    }

    private int matchChoiceScore(int selection) {
        HashMap<Integer, Integer> database = new HashMap<>();
        database.put(7, 3);
        database.put(6, 2);
        database.put(5, 1);
        database.put(4, 0);
        database.put(3, 1);
        database.put(2, 2);
        database.put(1, 3);
        return database.get(selection);
    }

    private ChoiceType matchChoiceType(int selection) {
        if (selection >= 1 && selection <= 3) {
            return ChoiceType.NEGATIVE;
        } else if (selection >= 5 && selection <= 7) {
            return ChoiceType.POSITIVE;
        } else {
            return ChoiceType.NONE;
        }
    }

    private Optional<Character> matchPersonalType(String twoSurveyType, ChoiceType choiceType) {
        if (choiceType == ChoiceType.NEGATIVE) {
            return Optional.of(twoSurveyType.charAt(0));
        } else if (choiceType == ChoiceType.POSITIVE) {
            return Optional.of(twoSurveyType.charAt(1));
        } else {
            return Optional.empty();
        }
    }
}
