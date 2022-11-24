package spring.study.serialize;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class JsonSerializeTest {
    @Test
    void deserializeJsonFormFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SpectrumChannelSetting model = objectMapper.readValue(getClass().getResourceAsStream("/anritsu-spectrum-setting.json"), SpectrumChannelSetting.class);

        assertEquals("S-Band TX", model.getChannel1().getDescription());
        assertEquals("S-Band RX RH", model.getChannel2().getDescription());
        assertEquals("S-Band RX LH", model.getChannel3().getDescription());
        assertEquals("X-Band RX RH", model.getChannel4().getDescription());
        assertEquals("X-Band RX LH", model.getChannel5().getDescription());

        log.info(model.getChannel1().toString());
        log.info(model.getChannel2().toString());
        log.info(model.getChannel3().toString());
        log.info(model.getChannel4().toString());
        log.info(model.getChannel5().toString());
    }
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
