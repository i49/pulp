package com.github.i49.pulp.core;

public class NullArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	
	public NullArgumentException(String name) {
		super(Messages.NULL_ARGUMENT(name));
	}

}
