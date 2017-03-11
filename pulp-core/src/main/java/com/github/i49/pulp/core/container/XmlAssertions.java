package com.github.i49.pulp.core.container;

import static com.github.i49.pulp.core.container.Message.*;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.core.xml.AbstractAttr;
import com.github.i49.pulp.core.xml.AbstractElement;

/**
 * Assertions on an XML element.
 */
class XmlAssertions {
	
	
	public static ElementAssertion assertOn(Element element) {
		return new ElementAssertion(element);
	}

	public static class ElementAssertion {
	
		private final Element actual;
		
		private ElementAssertion(Element actual) {
			this.actual = actual;
		}
		
		public ElementAssertion hasName(String localName, String namespaceURI) {
			if (!localName.equals(actual.getLocalName()) || !namespaceURI.equals(actual.getNamespaceURI())) {
				failWithMessage(XML_ELEMENT_UNEXPECTED.format(actual, element(localName, namespaceURI)));
			}
			return this;
		}
		
		public ElementAssertion hasAttribute(String localName) {
			if (!actual.hasAttribute(localName)) {
				failWithMessage(XML_ATTRIBUTE_MISSING.format(actual, attribute(localName)));
			}
			return this;
		}
		
		public ElementAssertion hasNonEmptyAttribute(String localName) {
			hasAttribute(localName);
			String value = actual.getAttribute(localName);
			if (value.isEmpty()) {
				failWithMessage(XML_ATTRIBUTE_EMPTY.format(actual, attribute(localName)));
			}
			return this;
		}
	}
	
	private static Element element(String localName, String namespaceURI) {
		return new XmlElement(localName, namespaceURI);
	}

	private static Attr attribute(String localName) {
		return new XmlAttribute(localName, null);
	}

	private static void failWithMessage(String message) {
		throw new EpubException(message);
	}

	private static class XmlElement extends AbstractElement {
		
		private final String localName;
		private final String namespaceURI;
		
		public XmlElement(String localName, String namespaceURI) {
			this.localName = localName;
			this.namespaceURI = namespaceURI;
		}
		
		@Override
		public String getLocalName() {
			return localName;
		}

		@Override
		public String getNamespaceURI() {
			return namespaceURI;
		}
	}

	private static class XmlAttribute extends AbstractAttr {

		private final String localName;
		private final String namespaceURI;

		public XmlAttribute(String localName, String namespaceURI) {
			this.localName = localName;
			this.namespaceURI = namespaceURI;
		}

		@Override
		public String getLocalName() {
			return localName;
		}

		@Override
		public String getNamespaceURI() {
			return namespaceURI;
		}
	}
}
