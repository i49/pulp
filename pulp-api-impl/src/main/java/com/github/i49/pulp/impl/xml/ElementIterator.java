/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.impl.xml;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class ElementIterator implements Iterator<Element> {

	private final NodeList nodeList;
	private final String namespaceURI;
	
	private int index;
	private Element next;
	
	ElementIterator(Element parent, String namespaceURI) {
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
