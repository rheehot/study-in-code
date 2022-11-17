package spring.study.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class JsonSerializeTest {
    @Test
    void objectToJson() throws JsonProcessingException {
        Compose compose = new Compose(
                new Unit("A", 1),
                new Unit("B", 2)
        );

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(compose);
        Assertions.assertThat(json).isEqualTo("""
                {
                  "unit1" : {
                    "name" : "A",
                    "age" : 1
                  },
                  "unit2" : {
                    "name" : "B",
                    "age" : 2
                  }
                }
                """.replaceAll("[\n ]", ""));
    }


    @RequiredArgsConstructor
    @Getter
    static class Compose {
        private final Unit unit1;
        private final Unit unit2;
    }

    @RequiredArgsConstructor
    @Getter
    static class Unit {
        private final String name;
        private final int age;
    }
}
