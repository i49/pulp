package com.github.i49.pulp.core.xml;

import static com.github.i49.pulp.core.Messages.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementAssertion extends AbstractAssertion {

	private final Element actual;
	
	ElementAssertion(Element actual) {
		this.actual = actual;
	}
	
	public ElementAssertion hasName(String localName) {
		return hasName(localName, actual.getNamespaceURI());
	}
	
	public ElementAssertion hasName(String localName, String namespaceURI) {
		if (!match(actual, localName, namespaceURI)) {
			failWithMessage(XML_ELEMENT_UNEXPECTED(getDocumentURI(), actual.getLocalName(), localName));
		}
		return this;
	}
	
	public ElementAssertion contains(String... elements) {
		Set<String> elementSet = new HashSet<>(Arrays.asList(elements));
		NodeList children = actual.getChildNodes();
		String namespaceURI = actual.getNamespaceURI();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			if (child.getNamespaceURI().equals(namespaceURI)) {
				elementSet.remove(child.getLocalName());
			}
		}
		if (elementSet.size() > 0) {
			failWithMessage(XML_ELEMENT_MISSING(getDocumentURI(), actual.getLocalName(), elementSet));
		}
		return this;
	}
	
	public ElementAssertion hasAttribute(String localName) {
		if (!actual.hasAttribute(localName)) {
			failWithMessage(XML_ATTRIBUTE_MISSING(getDocumentURI(), actual.getLocalName(), localName));
		}
		return this;
	}
	
	public ElementAssertion hasNonEmptyAttribute(String localName) {
		hasAttribute(localName);
		String value = actual.getAttribute(localName);
		if (value.isEmpty()) {
			failWithMessage(XML_ATTRIBUTE_EMPTY(getDocumentURI(), actual.getLocalName(), localName));
		}
		return this;
	}
	
	private boolean match(Node node, String localName, String namespaceURI) {
		if (!node.getLocalName().equals(localName)) {
			return false;
		}
		if (namespaceURI == null) {
			return node.getNamespaceURI() == null;
		} else {
			return namespaceURI.equals(node.getNamespaceURI());
		}
	}
	
	private String getDocumentURI() {
		return actual.getOwnerDocument().getDocumentURI();
	}
}
