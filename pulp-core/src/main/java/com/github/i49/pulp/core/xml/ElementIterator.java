package com.github.i49.pulp.core.xml;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementIterator implements Iterator<Element> {

	private final NodeList nodeList;
	private final String namespaceURI;
	
	private int index;
	private Element next;
	
	public ElementIterator(Element parent) {
		this(parent, null);
	}

	public ElementIterator(Element parent, String namespaceURI) {
		this.nodeList = parent.getChildNodes();
		this.namespaceURI = namespaceURI;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext() {
		if (this.next != null) {
			return true;
		}
		while (this.index < nodeList.getLength()) {
			Element element = filter(nodeList.item(this.index++));
			if (element != null) {
				this.next = element;
				break;
			}
		}
		return this.next != null;
	}

	@Override
	public Element next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Element next = this.next;
		this.next = null;
		return next;
	}
	
	private Element filter(Node node) {
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		if (this.namespaceURI == null) {
			if (node.getNamespaceURI() != null) {
				return null;
			}
		} else if (!this.namespaceURI.equals(node.getNamespaceURI())) {
			return null;
		}
		return (Element)node;
	}
}
