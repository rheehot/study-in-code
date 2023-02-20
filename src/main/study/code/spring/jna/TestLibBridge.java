package code.spring.jna;

import com.sun.jna.Native;

public class TestLibBridge {
    private TestLibrary library;

    public void load() {
        library = Native.load("/AnritsuSpectrumAnalyzer.dll", TestLibrary.class);
    }

    public String hello() {
        return library.hello();
    }

    public int getInt() {
        return library.getInt();
    }
}
