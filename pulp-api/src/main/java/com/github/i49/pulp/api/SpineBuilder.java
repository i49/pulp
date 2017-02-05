package com.github.i49.pulp.api;

public interface SpineBuilder {
	
	SpineBuilder next(String name);

	SpineBuilder next(String name, boolean linear);
}
