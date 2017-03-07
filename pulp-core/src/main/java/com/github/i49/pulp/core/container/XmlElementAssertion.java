package com.github.i49.pulp.core.container;

import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;

/**
 * Assertions on an XML element.
 */
class XmlElementAssertion {

	private final Element actual;

	public static XmlElementAssertion assertThat(Element element) {
		return new XmlElementAssertion(element);
	}
	
	private XmlElementAssertion(Element actual) {
		this.actual = actual;
	}
	
	public XmlElementAssertion hasName(String localName, String namespaceURI) {
		if (!localName.equals(actual.getLocalName()) || !namespaceURI.equals(actual.getNamespaceURI())) {
			failWithMessage(Messages.xmlElementUnexpected(actual, localName, namespaceURI));
		}
		return this;
	}
	
	public XmlElementAssertion hasAttribute(String name) {
		if (!actual.hasAttribute(name)) {
			failWithMessage(Messages.xmlAttributeMissing(actual, name));
		}
		return this;
	}
	
	public XmlElementAssertion hasNonEmptyAttribute(String name) {
		hasAttribute(name);
		String value = actual.getAttribute(name);
		if (value.isEmpty()) {
			failWithMessage(Messages.xmlAttributeEmpty(actual, name));
		}
		return this;
	}
	
	protected void failWithMessage(String message) {
		throw new EpubException(message);
	}
}
