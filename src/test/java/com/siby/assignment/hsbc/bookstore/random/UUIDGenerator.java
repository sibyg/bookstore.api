package com.siby.assignment.hsbc.bookstore.random;

import java.util.UUID;

public class UUIDGenerator extends Generator<UUID> {

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }
}
