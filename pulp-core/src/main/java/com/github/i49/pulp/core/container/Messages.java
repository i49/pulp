package com.github.i49.pulp.core.container;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.w3c.dom.Element;

final class Messages {

	private static final String BUNDLE_BASE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);

	static String containerNotFound(Path path) {
		return localize("container.not.found", path);
	}

	static String containerNotReadable(Path path) {
		return localize("container.not.readable", path);
	}

	static String containerEntryNotFound(String location) {
		return localize("container.entry.not.found", location);
	}

	static String containerEntryNotReadable(String location) {
		return localize("container.entry.not.readable", location);
	}

	static String xmlElementUnexpected(Element element, String expectedName, String expectedNamespaceURI) {
		return localize("xml.element.unexpected", element(element), element(expectedName, expectedNamespaceURI));
	}
	
	static String xmlAttributeMissing(Element element, String name) {
		return localize("xml.attribute.missing", element(element), attribute(name));
	}

	static String xmlAttributeEmpty(Element element, String name) {
		return localize("xml.attribute.empty", element(element), attribute(name));
	}
	
	static String xmlDocumentVersionUnsupported(String version) {
		return localize("xml.document.version.unsupported", version);
	}
	
	static String xmlDocumentNotWellFormed() {
		return localize("xml.document.not.well.formed");
	}
	
	static String containerNotWriteable(Path path) {
		return localize("container.not.writeable", path);
	}
	
	static String containerNotCloseable(Path path) {
		return localize("container.not.closeable", path);
	}
	
	private static String element(Element element) {
		return element(element.getLocalName(), element.getNamespaceURI());
	}
	
	private static String element(String localName, String namespaceURI) {
		if (namespaceURI == null) {
			String pattern = bundle.getString("xml.element.name");
			return MessageFormat.format(pattern, localName);
		} else {
			String pattern = bundle.getString("xml.element.qualified.name");
			return MessageFormat.format(pattern, localName, namespaceURI);
		}
	}

	private static String attribute(String localName) {
		String pattern = bundle.getString("xml.attribute.name");
		return MessageFormat.format(pattern, localName);
	}
	
	private static String localize(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		if (pattern == null) {
			return null;
		}
		return MessageFormat.format(pattern, arguments);
	}
}
