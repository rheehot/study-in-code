package tech.java.file;

import code.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SameFileTest {

    @Test
    void sameFile() throws IOException {
        // Given: 동일한 내용의 두 파일
        String sameContents = "Hello World";
        File file1 = FileUtil.newFile("./test1.txt", sameContents);
        File file2 = FileUtil.newFile("./test2.txt", sameContents);

        // Then: 두 파일의 내용이 동일한지 비교
        assertTrue(FileUtils.contentEquals(file1, file2));

        // 파일 삭제
        assertTrue(file1.delete());
        assertTrue(file2.delete());
    }

    @Test
    void diffFile() throws IOException {
        // 다른 내용의 두 파일
        String sameContents = "Hello World";
        File file1 = FileUtil.newFile("./test1.txt", sameContents);
        File file2 = FileUtil.newFile("./test2.txt", sameContents + "diff");

        // Then: 두 파일의 내용이 다른지 비교
        assertFalse(FileUtils.contentEquals(file1, file2));

        // 파일 삭제
        assertTrue(file1.delete());
        assertTrue(file2.delete());
    }
}
