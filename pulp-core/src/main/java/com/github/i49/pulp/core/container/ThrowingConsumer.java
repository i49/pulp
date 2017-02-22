package com.github.i49.pulp.core.container;

@FunctionalInterface
public interface ThrowingConsumer<T> {

	void accept(T t) throws Exception;
}
