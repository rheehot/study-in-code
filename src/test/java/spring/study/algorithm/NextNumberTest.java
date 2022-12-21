package spring.study.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 리팩토링
 -
 */
public class NextNumberTest {
    @Test
    void tester() {
        int solution = solution(new int[]{1, 2, 3, 4});
        Assertions.assertEquals(5, solution);
    }

    /*
    문제해결
    - 우선 주어진 입력이 등차 수열인지, 등비 수열인지 판단합니다.
    - 등차 또는 등비를 구합니다.
    - 마지막 항목에서 등차 또는 등비를 사용해 해답을 구합니다.
     */
    enum SequenceType {
        ARITHMETICAL, // 등차수열
        GEOMETRIC     // 등비수열
    }

    public int solution(int[] sequence) {
        SequenceType sequenceType = checkSequenceType(sequence);
        int regularParam = calculateRegularParam(sequence, sequenceType);
        return getNextNum(sequence[sequence.length - 1], sequenceType, regularParam);
    }

    private SequenceType checkSequenceType(int[] sequence) {
        assert sequence.length > 2;

        int diff = sequence[1] - sequence[0];
        if (sequence[2] - sequence[1] == diff) {
            return SequenceType.ARITHMETICAL;
        } else {
            return SequenceType.GEOMETRIC;
        }
    }

    private int calculateRegularParam(int[] sequence, SequenceType type) {
        assert type == SequenceType.ARITHMETICAL || type == SequenceType.GEOMETRIC;
        assert sequence[0] != 0;

        if (type == SequenceType.ARITHMETICAL) {
            return sequence[1] - sequence[0];
        } else {
            return sequence[1] / sequence[0];
        }
    }

    private int getNextNum(int lastItem, SequenceType type, int regularParam) {
        assert type == SequenceType.ARITHMETICAL || type == SequenceType.GEOMETRIC;

        if (type == SequenceType.ARITHMETICAL) {
            return lastItem + regularParam;
        } else {
            return lastItem * regularParam;
        }
    }
}
