package spring.study.solving;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonalInformationCollectionValidityPeriodTest {
    @Test
    void tester() {
        int[] solution = solution("2022.05.19", new String[]{"A 6", "B 12", "C 3"},
                                  new String[]{"2021.05.02 A",
                                               "2021.07.01 B",
                                               "2022.02.19 C",
                                               "2022.02.20 C"});
        System.out.println(Arrays.toString(solution));
        Assertions.assertArrayEquals(new int[]{1,3}, solution);
    }

    /*
     * 오늘 날짜와 약관(식별자, 보관개월 수) 목록 그리고 개인정보 (수집일, 약관 종류) 목록을 받아서 오늘 파기해야하는 개인정보의 번호
     * (i(index)+1) 목록을 반환합니다.
     *
     * 문제 해결 전략
     * - 각 개인정보의 파기 날짜를 계산합니다.
     * - 오늘 날짜와 비교하여 파기 여부를 판단합니다.
     * - 배열 인덱스에서 1씩 더하여 개인정보 번호를 반환합니다.
     *
     * 회고
     * - 문제 해결 과정 주석을 함수 내에 다는 것이 더 좋겠다.
     * - 문제 해결 과정 호흡이 길어서 중간 중간 과정에 집중이 흐려짐을 느낀다.
     * - 단계 단계를 검증하면서 나아가면 더 좋았겠다.
     *
     * 소요 시간
     * - 72분 소요
     *
     * 부족했던 점
     * - 날짜 전후를 계산하는 로직에서 절어서 한 10분을 날렸고 긴장되었다.
     */
    public int[] solution(String today, String[] strTerms, String[] strPrivacies) {

        // 특별히 유의해야할 제약사항은 보이지 않습니다.

        class Privacy {
            String term;
            LocalDate collectedDate;
        }

        // 1. 각 개인정보의 파기 날짜를 계산합니다.
        // 약관을 참조하기 쉽도록 맵으로 만듭니다.
        HashMap<String, Integer> termsMap = new HashMap<>();
        Arrays.stream(strTerms)
                .forEach(strTerm -> {
                    String[] idAndLimitMonth = strTerm.split(" ");
                    termsMap.put(idAndLimitMonth[0], Integer.parseInt(idAndLimitMonth[1]));
                });

        // 개인정보를 참조하기 쉽도록 객체 목록으로 만듭니다.
        List<Privacy> privacies = new ArrayList<>();
        Arrays.stream(strPrivacies)
                .forEach( strPrivacy -> {
                    String[] collectionAndTerm = strPrivacy.split(" ");
                    Privacy privacy = new Privacy();
                    String[] dateItems = collectionAndTerm[0].split("\\.");
                    privacy.collectedDate = LocalDate.of(Integer.parseInt(dateItems[0]), // 년
                                                         Integer.parseInt(dateItems[1]), // 원
                                                         Integer.parseInt(dateItems[2])); // 일
                    privacy.term = collectionAndTerm[1];
                    privacies.add(privacy);
                });


        // 개인정보.약관을 참조하여 개인정보.번호와 파기날짜로 구성된 객체를 생성한다.

        class PrivacyDueDate {
            int privacyId;
            LocalDate dueDate;
        }

        List<PrivacyDueDate> privacyDueDates = new ArrayList<>();

        IntStream.range(0, privacies.size())
                 .forEach(index -> {
                     PrivacyDueDate privacyDueDate = new PrivacyDueDate();

                     privacyDueDate.dueDate = toDueDate(privacies.get(index).collectedDate,
                                                        termsMap.get(privacies.get(index).term));
                     privacyDueDate.privacyId = index + 1;

                     privacyDueDates.add(privacyDueDate);
                 });

        // 오늘 날짜와 비교하여 파기 여부를 판단합니다.
        String[] todayItems = today.split("\\.");
        LocalDate todayDate = LocalDate.of(Integer.parseInt(todayItems[0]),
                                       Integer.parseInt(todayItems[1]),
                                       Integer.parseInt(todayItems[2]));

        List<Integer> dueDatePrivacies = new ArrayList<>();
        privacyDueDates.forEach( privacyDueDate -> {
            // 날짜 비교는 어떻게 수행할 것인가? 개인정보 파기 날짜보다 작거나 같은지 비교해야 한다.
            if (privacyDueDate.dueDate.isBefore(todayDate)) {
                dueDatePrivacies.add(privacyDueDate.privacyId);
            }
        });

        // 배열 인덱스에서 1씩 더하여 개인정보 번호를 반환합니다.
        int[] answer = new int[dueDatePrivacies.size()];
        IntStream.range(0, dueDatePrivacies.size())
                         .forEach( index -> {
                             answer[index] = dueDatePrivacies.get(index);
                         });
        return answer;
    }

    LocalDate toDueDate(LocalDate startDate, int month) {
        LocalDate dueDate = startDate.plusMonths(month).minusDays(1);
        if (startDate.getDayOfMonth() == 1) {
            dueDate = LocalDate.of(dueDate.getYear(), dueDate.getMonth(), 28);
        }
        return dueDate;
    }

    @Test
    void monthAdd() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        LocalDate addedDate = toDueDate(date, 12);
        Assertions.assertEquals(LocalDate.of(2021, 12, 28), addedDate);
        System.out.println(addedDate);
    }
}
