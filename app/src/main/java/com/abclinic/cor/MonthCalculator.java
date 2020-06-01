package com.abclinic.cor;

import java.time.Period;

public class MonthCalculator extends AbstractTimeCalculator {
    @Override
    protected long getMax() {
        return 12;
    }

    @Override
    protected Method getMethod() {
        return Method.PERIOD;
    }

    @Override
    public long getValue(Period period) {
        return period.getMonths();
    }

    @Override
    protected String getResult() {
        return "tháng";
    }
}
