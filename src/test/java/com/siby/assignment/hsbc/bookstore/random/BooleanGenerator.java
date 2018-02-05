package com.siby.assignment.hsbc.bookstore.random;

public class BooleanGenerator extends Generator<Boolean> {
    @Override
    public Boolean next() {
        return RANDOM.nextBoolean();
    }
}
