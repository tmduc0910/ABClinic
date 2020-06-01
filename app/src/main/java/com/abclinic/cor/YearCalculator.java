package com.abclinic.cor;

import java.time.Period;

public class YearCalculator extends AbstractTimeCalculator {
    @Override
    protected long getMax() {
        return Long.MAX_VALUE;
    }

    @Override
    protected Method getMethod() {
        return Method.PERIOD;
    }

    @Override
    protected String getResult() {
        return "năm";
    }

    @Override
    public long getValue(Period period) {
        return period.getYears();
    }
}
