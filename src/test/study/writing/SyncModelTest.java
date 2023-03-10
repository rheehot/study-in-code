package writing;

import code.writing.synchronous.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SyncModelTest {
    @Test
    void synchronous() {
        Server server = new Server();
        long result = server.work(10, 10_000_000);
        Assertions.assertEquals(50_000_008_628_800L, result);
    }

    @Test
    void asynchronous() {
        Server server = new Server();
        long result = server.workAsync(10, 10_000_000);
        Assertions.assertEquals(50_000_008_628_800L, result);
    }
}
