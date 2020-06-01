package com.abclinic.cor;

import com.abclinic.dto.TimeDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public abstract class AbstractTimeCalculator implements ITimeConverter {
    protected AbstractTimeCalculator nextCalculator;

    public static AbstractTimeCalculator getCalculator() {
        SecondCalculator secondCalculator = new SecondCalculator();
        MinuteCalculator minuteCalculator = new MinuteCalculator();
        HourCalculator hourCalculator = new HourCalculator();
        DayCalculator dayCalculator = new DayCalculator();
        MonthCalculator monthCalculator = new MonthCalculator();
        YearCalculator yearCalculator = new YearCalculator();

        secondCalculator.setNextCalculator(minuteCalculator);
        minuteCalculator.setNextCalculator(hourCalculator);
        hourCalculator.setNextCalculator(dayCalculator);
        dayCalculator.setNextCalculator(monthCalculator);
        monthCalculator.setNextCalculator(yearCalculator);

        return secondCalculator;
    }

    public TimeDto execute(LocalDateTime from, LocalDateTime to) {
        long value = calculate(from, to);
        if (canExecute(value, getMax())) {
            return new TimeDto(value, getResult());
        } else if (nextCalculator != null) {
            return nextCalculator.execute(from, to);
        }
        return null;
    }

    private long calculate(LocalDateTime from, LocalDateTime to) {
        switch (getMethod()) {
            case PERIOD:
                return getValue(Period.between(from.toLocalDate(), to.toLocalDate()));
            case DURATION:
                return getValue(Duration.between(from, to));
        }
        return 0;
    }

    private boolean canExecute(long value, long max) {
        return value < max;
    }

    protected abstract String getResult();

    protected abstract long getMax();

    protected abstract Method getMethod();

    public void setNextCalculator(AbstractTimeCalculator nextCalculator) {
        this.nextCalculator = nextCalculator;
    }

    enum Method {
        DURATION,
        PERIOD
    }
}
