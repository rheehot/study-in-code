package spring.study.solving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    /*
     # 문제점
     - 비지니스 로직이 코드에 모두 반영되었는지 확인이 잘 되지 않고 있다.
     */
    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(String[] idList, String[] report, int k, int[] expected) {
        int[] result = solution(idList, report, k);
        Assertions.assertArrayEquals(expected, result);
    }

    /**
     * 게시판의 사용자 목록(ID)과 신고 정보 목록 그리고 사용정지에 해당하는 신고횟수를 입력 받아서 각 사용자 별 신고 결과 알림을
     * 받는 횟수를 반환합니다.
     * @param users 사용자 목록
     * @param report "A B"인 경우 A가 B를 신고한 정보를 뜻합니다. 신고 정보가 중복되는 경우 사용정지를 위한 통계에 포함시키지 않습니다. 자신이 자기를 신고하는 경우는 없습니다.
     * @param dropOutNum 해당 숫자 이상 신고를 받은 사용자는 사용정지 대상이 됩니다.
     * @return 사용정지된 사용자를 신고한 횟수
     */
    public int[] solution(String[] users, String[] report, int dropOutNum) {
        // "A B" (A가 B를 신고) 형식의 리포트를 분석해서 누가, 누구를 신고했는지 정보를 모읍니다.
        // 사용자 별로 자신을 신고한 다른 사용자 목록을 알고 있어야 합니다.
        HashMap<String, List<String>> myReportUserMap = new HashMap<>();
        IntStream.range(0, users.length)
                 .forEach(i -> myReportUserMap.put(users[i], new ArrayList<>()));

        IntStream.range(0, report.length)
                .forEach(i -> {
                    String[] aReportB = report[i].split(" ");
                    List<String> myReportUser = myReportUserMap.get(aReportB[1]);
                    if (!myReportUser.contains(aReportB[0])) {
                        myReportUser.add(aReportB[0]);
                    }
                });

        // 결국 사용 중지 당한 사용자를 신고한 사람을 카운팅 해야합니다.
        HashMap<String, Integer> alarmNumMap = new HashMap<>();
        IntStream.range(0, users.length)
                 .forEach(i -> alarmNumMap.put(users[i], 0));

        myReportUserMap.forEach( (user, myReportUsers) -> {
            if (myReportUsers.size() >= dropOutNum) {
                myReportUsers.forEach(reportUser -> {
                    int alarmNum = alarmNumMap.get(reportUser);
                    alarmNum++;
                    alarmNumMap.put(reportUser, alarmNum);
                });
            }
        });

        // 최종적으로 사용자 별로 사용정지된 사용자를 신고한 횟수를 반환합니다.
        int[] alarmNums = new int[users.length];
        IntStream.range(0, users.length)
                 .forEach(i -> alarmNums[i] = alarmNumMap.get(users[i]));

        return alarmNums;
    }
}
