package com.app.housie.core.generator;

/**
 * @param <V> Type of the value, implementation of this class will generate
 */
public interface Generator<V> {
    /**
     * @return generated value
     */
    V generate();
}
