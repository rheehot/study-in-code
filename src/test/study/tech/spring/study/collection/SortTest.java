package tech.spring.study.collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTest {
    @Test
    void sortStringList() {
        List<String> sample = new ArrayList<>(List.of("B", "C", "A"));
        Collections.sort(sample);
        Assertions.assertThat(sample).isEqualTo(List.of("A", "B", "C"));
    }
}
