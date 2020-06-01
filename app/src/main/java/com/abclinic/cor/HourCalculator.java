package com.abclinic.cor;

import java.time.Duration;

public class HourCalculator extends AbstractTimeCalculator {
    @Override
    protected long getMax() {
        return 24;
    }

    @Override
    protected Method getMethod() {
        return Method.DURATION;
    }

    @Override
    public long getValue(Duration duration) {
        return duration.toHours();
    }

    @Override
    protected String getResult() {
        return "giờ";
    }
}
