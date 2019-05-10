package com.lguti.pacman.helpers.collections;

import java.util.function.Function;


public interface Reducer<P, A, R> extends Function<P, R> {
    R apply(P p, A a);
}
