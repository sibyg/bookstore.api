package com.siby.assignment.hsbc.bookstore.random;

public class LongGenerator extends Generator<Long> {

    @Override
    public Long next() {
        return RANDOM.nextLong();
    }
}
