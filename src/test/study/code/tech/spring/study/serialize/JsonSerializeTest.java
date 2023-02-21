package code.tech.spring.study.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonSerializeTest {

    @Test
    void objectDeserialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String carJson = """
        {
            "name" : "스파크",
            "age" : 2014,
            "velocity" : 120.12
        }
        """;

        Car car = mapper.readValue(carJson, Car.class);
        System.out.println("car = " + car);
        Assertions.assertEquals("스파크", car.getName());
        Assertions.assertEquals(2014, car.getAge());
        Assertions.assertEquals(120.12, car.getVelocity());
    }

    @Test
    void arrayDeserialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonCommands = """
        [
            "HI", "HELLO"
        ]
        """;

        List<String> comments = mapper.readValue(jsonCommands, new TypeReference<>() {});
        Assertions.assertEquals(2, comments.size());
        Assertions.assertEquals("HI", comments.get(0));
        Assertions.assertEquals("HELLO", comments.get(1));
    }

    @Test
    void objectArrayDeserialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonCommands = """
        [
            {
                "name" : "스파크",
                "age" : 2014,
                "velocity" : 120.12
            },
            {
                "name" : "그랜저",
                "age" : 2015,
                "velocity" : 140.14
            }
        ]
        """;

        List<Car> comments = mapper.readValue(jsonCommands, new TypeReference<>() {});
        Assertions.assertEquals(2, comments.size());
        Assertions.assertEquals(new Car("스파크", 2014, 120.12), comments.get(0));
        Assertions.assertEquals(new Car("그랜저", 2015, 140.14), comments.get(1));
    }

    @Test
    void innerClassDeserializeException() throws JsonProcessingException {
        @ToString
        @Getter
        class Car {
            private String name;
            private double velocity;
            private int age;
        }

        ObjectMapper mapper = new ObjectMapper();
        String carJson = """
                {
                    "name" : "스파크",
                    "age" : 2014,
                    "velocity" : 120.12
                }""";

        Assertions.assertThrows(JsonMappingException.class,
                                () -> mapper.readValue(carJson, Car.class));
    }

    @Test
    void deserializeJsonFormFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SpectrumChannelSetting model = objectMapper.readValue(getClass().getResourceAsStream("/anritsu-spectrum-setting.json"), SpectrumChannelSetting.class);

        Assertions.assertEquals("S-Band TX", model.getChannel1().getDescription());
        Assertions.assertEquals("S-Band RX RH", model.getChannel2().getDescription());
        Assertions.assertEquals("S-Band RX LH", model.getChannel3().getDescription());
        Assertions.assertEquals("X-Band RX RH", model.getChannel4().getDescription());
        Assertions.assertEquals("X-Band RX LH", model.getChannel5().getDescription());

        log.info(model.getChannel1().toString());
        log.info(model.getChannel2().toString());
        log.info(model.getChannel3().toString());
        log.info(model.getChannel4().toString());
        log.info(model.getChannel5().toString());
    }

    @SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
    @Test
    void objectToJson() throws JsonProcessingException {
        Compose compose = new Compose(
                new Unit("A", 1),
                new Unit("B", 2)
        );

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(compose);
        Assertions.assertEquals("""
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
                """.replaceAll("[\n ]", ""), json);
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
