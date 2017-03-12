package com.github.i49.pulp.core.xml;

import org.w3c.dom.Element;

public final class XmlAssertions {
	
	public static ElementAssertion assertOn(Element actual) {
		return new ElementAssertion(actual);
	}

	private XmlAssertions() {
	}
}
