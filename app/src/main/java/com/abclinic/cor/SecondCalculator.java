package com.abclinic.cor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SecondCalculator extends AbstractTimeCalculator {
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
        return "giây";
    }

    @Override
    public long getValue(Duration duration) {
        return duration.get(ChronoUnit.SECONDS);
    }
}
