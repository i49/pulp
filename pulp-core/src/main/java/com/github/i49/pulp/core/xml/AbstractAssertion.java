package com.github.i49.pulp.core.xml;

abstract class AbstractAssertion {

	protected void failWithMessage(String message) {
		throw new AssertionFailureException(message);
	}
}
