package com.github.i49.pulp.core.container;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Utility class for manipulating XML elements.
 */
public final class Elements {

	public static Element firstChildElement(Element e) {
		Element child = null;
		for (Node node = e.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				child = (Element)node;
				break;
			}
		}
		return child;
	}

	public static Element firstChildElement(Element e, String namespaceURI) {
		Element child = null;
		for (Node node = e.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE && namespaceURI.equals(node.getNamespaceURI())) {
				child = (Element)node;
				break;
			}
		}
		return child;
	}
	
	public static List<Element> childElements(Element e) {
		List<Element> children = new ArrayList<>();
		for (Node node = e.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				children.add((Element)node);
			}
		}
		return children;
	}
	
	public static List<Element> childElements(Element e, String namespaceURI) {
		List<Element> children = new ArrayList<>();
		for (Node node = e.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE && namespaceURI.equals(node.getNamespaceURI())) {
				children.add((Element)node);
			}
		}
		return children;
	}
	
	private Elements() {
	}
}
