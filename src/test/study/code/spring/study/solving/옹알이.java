package code.spring.study.solving;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * 부족한점
 * - for 문의 흐름과 언제 break 분기해야 할지 전체 입구를 잘 파악하지 못하고 주먹구구식으로 나아가는 경향이 보인다.
 * - 리팩토링을 한 번에 두 가지씩 도전하면 무너진다.
 * 느낀점
 * - 시간을 두고 완벽하게 고치려니 오히려 문제 해결이 안된다.
 * - 선언적으로 함수형 프로그래밍 지원을 받아 코드를 작성하니 for 문의 조건 및 break 지점에서 오는 인지 부하가 줄어든다.
 * 잘한점
 * - 선언적으로 코드를 고치려고 노력하니 가독성이 좋고 안정적인 코드가 만들어 진 것 같다.
 * 배운점
 * - Stream 활용한 데이터 다루기 (filter, count)
 * - 배열에서 Stream 생성하기 (Arrays.stream())
 * - 각 요소에서 Stream 생성하기 (Stream.of())
 * - 다시 한 번 기억하게 된다. String 객체는 불변성을 가진다.
 */
public class 옹알이 {
    @Test
    void tester() {
        int solution = solution(new String[]{"ayaye", "uuuma", "ye", "yemawoo", "ayaa"});
        Assertions.assertEquals(3, solution);
    }

    // 최종 해결책
    public int solution(String[] sentences) {
        return (int) Arrays.stream(sentences)
                                .filter(this::canSpeakSentence)
                                .count();
    }

    private boolean canSpeakSentence(String targetSentence) {
        String gettingCut = targetSentence;
        while (true) {
            String subSentence = gettingCut;
            // 문장이 말할 수 있는 발음으로 시작하는지 찾기
            Optional<String> startedWord = Stream.of("aya", "ye", "woo", "ma")
                                                   .filter(word -> started(subSentence, word))
                                                   .findAny();

            // 문장이 말할 수 있는 발음으로 시작하지 않는다면 문장을 말할 수 없습니다.
            if (startedWord.isEmpty()) {
                return false;
            }

            // 이어지는 문장이 발음 가능한지 검사하기 위해 찾아진 발음을 문장에서 제거
            gettingCut = gettingCut.substring(startedWord.get().length());
            if (gettingCut.isEmpty()) {
                return true;
            }
        }
    }

    boolean started(String targetSentence, String canSpeakWord) {
        if (targetSentence.length() < canSpeakWord.length()) {
            return false;
        }

        String targetSub = targetSentence.substring(0, canSpeakWord.length());
        return targetSub.equals(canSpeakWord);
    }

    // 빠르게 주먹구구식으로 문제를 해결한 코드입니다.
    public int solution1(String[] inputs) {
        int count = 0;

        for (String input : inputs) {
            int nextIndex = 0;
            while (true) {
                if (nextIndex >= input.length()) {
                    count++;
                    // 성공
                    break;
                }

                if ((nextIndex = scanWord(input, nextIndex)) == -1) {
                    // 실패
                    break;
                }
            }
        }
        return count;
    }

    private int scanWord(String input, int nextIndex) {
        List<String> speakableWords = List.of("aya", "ye", "woo", "ma");

        char firstChar = input.charAt(nextIndex);
        int i;
        for (i = 0; i<speakableWords.size(); i++) {
            String speakableWord = speakableWords.get(i);
            if (speakableWord.charAt(0) == firstChar) {
                if (input.length() < nextIndex + speakableWord.length()) {
                    nextIndex = -1;
                    break;
                }
                String substring = input.substring(nextIndex, nextIndex + speakableWord.length());
                if (speakableWord.equals(substring)) {
                    nextIndex += speakableWord.length();
                    break;
                }
            }
        }

        if (i == speakableWords.size()) {
            return -1;
        } else {
            return nextIndex;
        }
    }
}
