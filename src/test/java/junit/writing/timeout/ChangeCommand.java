package junit.writing.timeout;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChangeCommand {
    private final int value;
}
