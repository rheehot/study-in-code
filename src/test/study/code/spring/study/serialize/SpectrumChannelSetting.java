package code.spring.study.serialize;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("unused")
@Getter
@ToString
public class SpectrumChannelSetting {
    private SettingUnit channel1;
    private SettingUnit channel2;
    private SettingUnit channel3;
    private SettingUnit channel4;
    private SettingUnit channel5;

    @Getter
    @ToString
    public static class SettingUnit {
        private String description;
        private int startFrequency;
        private int endFrequency;
        private int rbw;
        private int vbw;
        private int refLevel;
        private String preamp;
        private int points;
        private int traceCount;
        private List<Double> traces;
        private double channelPower;
    }
}
