package com.github.i49.pulp.core.container;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * Messages for this package.
 */
enum Message {
	
	CONTAINER_NOT_FOUND,
	CONTAINER_NOT_READABLE,
	CONTAINER_NOT_WRITEABLE,
	CONTAINER_NOT_CLOSEABLE,
	
	XML_DOCUMENT_VERSION_UNSUPPORTED,
	XML_DOCUMENT_NOT_WELL_FORMED,
	
	XML_ELEMENT_UNEXPECTED,
	XML_ATTRIBUTE_MISSING,
	XML_ATTRIBUTE_EMPTY
	;

	private static final String BASE_NAME = Message.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME);
	
	public String format(Object... args) {
		for (int i = 0; i < args.length; i++) {
			args[i] = formatObject(args[i]);
		}
		return MessageFormat.format(bundle.getString(name()), args);
	}
	
	private static Object formatObject(Object object) {
		if (object instanceof Element) {
			object = formatElement((Element)object);
		} else if (object instanceof Attr) {
			formatAttribute((Attr)object);
		}
		return object;
	}
	
	private static String formatElement(Element element) {
		return formatElement(element.getLocalName(), element.getNamespaceURI());
	}
	
	private static String formatAttribute(Attr attribute) {
		return formatAttribute(attribute.getLocalName(), attribute.getNamespaceURI());
	}

	private static String formatElement(String localName, String namespaceURI) {
		if (namespaceURI == null) {
			String pattern = bundle.getString("XML_ELEMENT_NAME");
			return MessageFormat.format(pattern, localName);
		} else {
			String pattern = bundle.getString("XML_ELEMENT_QUALIFIED_NAME");
			return MessageFormat.format(pattern, localName, namespaceURI);
		}
	}
	
	private static String formatAttribute(String localName, String namespaceURI) {
		if (namespaceURI == null) {
			String pattern = bundle.getString("XML_ATTRIBUTE_NAME");
			return MessageFormat.format(pattern, localName);
		} else {
			String pattern = bundle.getString("XML_ATTRIBUTE_QUALIFIED_NAME");
			return MessageFormat.format(pattern, localName, namespaceURI);
		}
	}
}
