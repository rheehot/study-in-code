package spring.study.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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
    enum Type {
        ARITHMETICAL, GEOMETRIC
    }

    public int solution(int[] common) {
        Type type = checkType(common);
        int commonParam = checkCommonParam(common, type);
        return checkNextNum(common[common.length-1], type, commonParam);
    }

    private Type checkType(int[] common) {
        assert common.length > 2;

        int diff = common[1] - common[0];
        if (common[2] - common[1] == diff) {
            return Type.ARITHMETICAL;
        } else {
            return Type.GEOMETRIC;
        }
    }

    private int checkCommonParam(int[] common, Type type) {
        assert type == Type.ARITHMETICAL || type == Type.GEOMETRIC;
        assert common[0] != 0;

        if (type == Type.ARITHMETICAL) {
            return common[1] - common[0];
        } else {
            return common[1] / common[0];
        }
    }

    private int checkNextNum(int lastItem, Type type, int commonParam) {
        assert type == Type.ARITHMETICAL || type == Type.GEOMETRIC;

        if (type == Type.ARITHMETICAL) {
            return lastItem + commonParam;
        } else {
            return lastItem * commonParam;
        }
    }
}
