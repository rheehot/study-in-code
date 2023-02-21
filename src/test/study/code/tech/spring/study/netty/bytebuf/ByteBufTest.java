package code.tech.spring.study.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ByteBufTest {

    /**
     * 아래 테스트 코드를 실행하면 java.lang.UnsupportedOperationException 예외가 발생하는데 디버깅 정보에 속한다고
     * Netty 프로젝트 이슈에 설명되어 있습니다. (<a href="https://github.com/netty/netty/issues/7817">#7817</a>)
     */
    @Test
    void decode() {
        // Given
        byte[] byteArray = new byte[]{0x30, 0x39};
        ByteBuf byteBuf = Unpooled.copiedBuffer(byteArray);

        // When
        short decoded = byteBuf.readShort();

        // Then
        Assertions.assertEquals(12345, decoded);

        // Clean
        byteBuf.release();
    }
}
