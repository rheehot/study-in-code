package code.tech.spring.study.solving;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GetReportResultTest {

    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                Arguments.arguments(new String[] { "muzi", "frodo", "apeach", "neo" },
                                    new String[] {
                                            "muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"
                                    },
                                    2,
                                    new int[] { 2, 1, 1, 0 }),
                Arguments.arguments(new String[] { "con", "ryan" },
                                    new String[] { "ryan con", "ryan con", "ryan con", "ryan con" },
                                    3,
                                    new int[] { 0, 0 })
        );
    }

    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(String[] idList, String[] report, int k, int[] expected) {
        int[] result = solution(idList, report, k);
        Assertions.assertArrayEquals(expected, result);
    }

    /**
     * 게시판의 사용자 목록(ID)과 신고 정보 목록 그리고 사용정지에 해당하는 신고횟수를 입력 받아서 각 사용자 별 신고 결과 알림을 받는 횟수를 반환합니다.
     * @param users 사용자 목록
     * @param report "A B"인 경우 A가 B를 신고한 정보를 뜻합니다. 신고 정보가 중복되는 경우 사용정지를 위한 통계에 포함시키지 않습니다. 자신이 자기를 신고하는 경우는 없습니다.
     * @param dropOutNum 해당 숫자 이상 신고를 받은 사용자는 사용정지 대상이 됩니다.
     * @return 사용정지된 사용자를 신고한 횟수
     */
    public int[] solution(String[] users, String[] report, int dropOutNum) {
        // 각 사용자를 신고한 사람 목록을 중복을 제외하고 수집합니다.
        HashMap<String, Set<String>> userAndReportersMap = new HashMap<>();
        IntStream.range(0, users.length)
                 .forEach(i -> userAndReportersMap.put(users[i], new HashSet<>()));

        IntStream.range(0, report.length).forEach(i -> {
            String[] reporterAndTargetUser = report[i].split(" ");
            String reporter = reporterAndTargetUser[0];
            String targetUser = reporterAndTargetUser[1];
            Set<String> reporters = userAndReportersMap.get(targetUser);
            reporters.add(reporter);
        });

        // 각 사용자의 사용중지될 사용자 신고 횟수를 카운트 합니다.
        HashMap<String, Integer> reporterAndAlarmCountMap = new HashMap<>();
        IntStream.range(0, users.length)
                 .forEach(i -> reporterAndAlarmCountMap.put(users[i], 0));
        userAndReportersMap.forEach( (user, reporters) -> {
            if (reporters.size() >= dropOutNum) {
                reporters.forEach(reporter -> {
                    int alarmNum = reporterAndAlarmCountMap.get(reporter);
                    alarmNum++;
                    reporterAndAlarmCountMap.put(reporter, alarmNum);
                });
            }
        });

        // 약속된 정수 배열로 반환합니다.
        int[] alarmNums = new int[users.length];
        IntStream.range(0, users.length)
                 .forEach(i -> alarmNums[i] = reporterAndAlarmCountMap.get(users[i]));

        return alarmNums;
    }
}
