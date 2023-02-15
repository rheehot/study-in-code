package writing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import writing.synchronous.Client;

public class SyncModelTest {
    @Test
    void synchronous() {
        Client client = new Client();
        long result = client.taskSync(10_000_000, 1_000_000);
        Assertions.assertEquals(50_500_005_500_000L, result);
    }

    @Test
    void asynchronous() {
        Client client = new Client();
        long result = client.taskAsync(10_000_000, 1_000_000);
        Assertions.assertEquals(50_500_005_500_000L, result);
    }
}
