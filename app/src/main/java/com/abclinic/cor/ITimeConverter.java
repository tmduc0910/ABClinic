package com.abclinic.cor;

import java.time.Duration;
import java.time.Period;

public interface ITimeConverter {
    default long getValue(Duration duration) {
        throw new UnsupportedOperationException();
    }

    default long getValue(Period period) {
        throw new UnsupportedOperationException();
    }
}
