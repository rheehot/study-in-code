package code.spring.study.serialize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModelMapperTest {
    @Test
    void deepMapping() {
        ModelMapper modelMapper = new ModelMapper();

        Src src = new Src();
        src.status = new Src.Status();
        src.status.setChannel(1);
        src.status.setFrequency("1000.1");
        src.status.setTrace(List.of(11.1, 22.2, 33.3));

        Dst dst = modelMapper.map(src, Dst.class);

        assertNotEquals(dst.getStatus().hashCode(), src.getStatus().hashCode()); // 별도의 생성 후 복사
        assertEquals(src.getStatus().getTrace().hashCode(), dst.getStatus().getTrace().hashCode()); // ImmutableCollections 객체 레퍼런스 복사

        assertEquals(src.status.getChannel(), dst.status.getChannel());
        assertEquals(src.status.getFrequency(), dst.status.getFrequency());
        assertEquals(src.status.getTrace(), dst.status.getTrace());
    }

    @ToString
    @Setter
    @Getter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    static class Src {
        private Status status = new Status();

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        @EqualsAndHashCode
        public static class Status {
            private int channel;
            private String frequency;
            private List<Double> trace;
        }
    }


    @ToString
    @Setter
    @Getter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    static class Dst {
        private Status status = new Status();

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        @EqualsAndHashCode
        public static class Status {
            private int channel;
            private String frequency;
            private List<Double> trace;
            private List<String> errors;
        }
    }

}
