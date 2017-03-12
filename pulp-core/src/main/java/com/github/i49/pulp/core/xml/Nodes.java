package com.github.i49.pulp.core.xml;

import java.util.Iterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public final class Nodes {

	public static Element newElement(String localName, String namespaceURI) {
		return new NominalElement(localName, namespaceURI);
	}
	
	public static Attr newAttribute(String localName, String namespaceURI) {
		return new NominalAttr(localName, namespaceURI);
	}
	
	public static Iterator<Element> children(Element element, String namespaceURI) {
		return new ElementIterator(element, namespaceURI);
	}

	private Nodes() {
	}
}
