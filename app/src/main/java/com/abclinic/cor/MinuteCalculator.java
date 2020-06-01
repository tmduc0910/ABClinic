package com.abclinic.cor;

import java.time.Duration;

public class MinuteCalculator extends AbstractTimeCalculator {
    @Override
    protected long getMax() {
        return 60;
    }

    @Override
    protected Method getMethod() {
        return Method.DURATION;
    }

    @Override
    protected String getResult() {
        return "phút";
    }

    @Override
    public long getValue(Duration duration) {
        return duration.toMinutes();
    }
}
