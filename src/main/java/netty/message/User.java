package netty.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import netty.handler.LogSource;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class User implements LogSource {
    private final String name;
    private final int age;

    @Override
    public String toMessage() {
        return "name = %s, age = %d".formatted(name, age);
    }
}
