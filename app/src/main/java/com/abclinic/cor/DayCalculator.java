package com.abclinic.cor;

import com.abclinic.dto.TimeDto;

import java.time.Duration;
import java.time.LocalDateTime;

public class DayCalculator extends AbstractTimeCalculator {
    private long max;

    @Override
    protected long getMax() {
        return max;
    }

    @Override
    protected Method getMethod() {
        return Method.DURATION;
    }

    @Override
    protected String getResult() {
        return "ngày";
    }

    @Override
    public long getValue(Duration duration) {
        return duration.toDays();
    }

    @Override
    public TimeDto execute(LocalDateTime from, LocalDateTime to) {
        this.max = from.getMonth().maxLength();
        return super.execute(from, to);
    }
}
