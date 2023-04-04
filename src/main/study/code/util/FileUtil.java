package code.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtil {
    private FileUtil() {
    }

    public static File newFile(String path, String content) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(content);
            return new File(path);
        }
    }
}
