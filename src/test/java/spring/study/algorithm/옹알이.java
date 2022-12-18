package spring.study.algorithm;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 부족한점
 * - for 문의 흐름과 언제 break 분기해야 할지 전체 입구를 잘 파악하지 못하고 주먹구구식으로 나아가는 경향이 보인다.
 */
public class 옹알이 {
    @Test
    void tester() {
        int solution = solution(new String[]{"ayaye", "uuuma", "ye", "yemawoo", "ayaa"});
        Assertions.assertEquals(3, solution);
    }

    public int solution(String[] inputs) {
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
