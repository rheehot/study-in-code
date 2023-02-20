package code.spring.jna;

import com.sun.jna.Library;

public interface TestLibrary extends Library {
    String hello();
    int getInt();
}
