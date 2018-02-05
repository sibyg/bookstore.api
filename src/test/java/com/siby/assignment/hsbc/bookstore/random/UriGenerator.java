package com.siby.assignment.hsbc.bookstore.random;

import static com.siby.assignment.hsbc.bookstore.random.RandomGenerator.STRING;
import static com.siby.assignment.hsbc.bookstore.random.RandomGenerator.values;
import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;

public class UriGenerator extends Generator<URI> {
    @Override
    public URI next() {
        try {
            return new URI(format("http://%s.%s", STRING.next(), values("com", "co.uk", "org").next()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
