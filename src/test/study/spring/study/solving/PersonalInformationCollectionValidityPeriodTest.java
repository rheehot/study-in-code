package spring.study.solving;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
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

    public int[] solution(String today, String[] strTerms, String[] strPrivacies) {

        // 약관 데이터 구조를 생성합니다.
        HashMap<String, Integer> termsMap = new HashMap<>();
        Arrays.stream(strTerms)
                .forEach(strTerm -> {
                    String[] idAndLimitMonth = strTerm.split(" ");
                    termsMap.put(idAndLimitMonth[0], Integer.parseInt(idAndLimitMonth[1]));
                });

        // 각 개인정보의 유효한 날짜를 계산합니다.
        List<LocalDate> validDates = Arrays.stream(strPrivacies)
                                           .map(strPrivacy -> strPrivacy.split(" "))
                                           .map(collectDateAndTerm -> toValidDate(collectDateAndTerm, termsMap))
                                           .collect(Collectors.toList());

        // 오늘 날짜와 비교하여 파기 여부를 판단합니다.
        List<Integer> destroyPrivacies = new ArrayList<>();
        LocalDate todayDate = toDate(today);
        IntStream.range(0, validDates.size())
                 .forEach(index -> {
                     if (validDates.get(index).isBefore(todayDate)) {
                         destroyPrivacies.add(index + 1);
                     }
                 });

        // 정수 배열로 변환하여 반환합니다.
        return destroyPrivacies.stream()
                               .mapToInt(i -> i)
                               .toArray();
    }

    LocalDate toValidDate(String[] collectDateAndTerm, HashMap<String, Integer> termsMap) {
        LocalDate collectDate = toDate(collectDateAndTerm[0]);
        String termId = collectDateAndTerm[1];
        Integer validMonth = termsMap.get(termId);

        LocalDate validDate = collectDate.plusMonths(validMonth).minusDays(1);
        if (collectDate.getDayOfMonth() == 1) {
            validDate = LocalDate.of(validDate.getYear(), validDate.getMonth(), 28);
        }
        return validDate;
    }

    LocalDate toDate(String date) {
        String[] todayItems = date.split("\\.");
        return LocalDate.of(Integer.parseInt(todayItems[0]), // 년
                            Integer.parseInt(todayItems[1]), // 월
                            Integer.parseInt(todayItems[2])); // 일
    }
}
