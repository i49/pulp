package com.github.i49.pulp.core;

import java.net.URI;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public final class Messages {

	private static final String BUNDLE_BASE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);

	public static String MISSING_PUBLICATION_RESOURCE(URI location) {
		return format("missing.publication.resource", location);
	}
	
	public static String DUPLICATE_PUBLICATION_RESOURCE(URI location) {
		return format("duplicate.publication.resource", location);
	}

	public static String MISSING_MANIFEST_ITEM(URI location) {
		return format("missing.manifest.item", location);
	}
	
	public static String DUPLICATE_MANIFEST_ITEM(URI location) {
		return format("duplicate.manifest.item", location);
	}
	
	public static String INVALID_RESOURCE(URI identifier) {
		return format("invalid.resource", identifier);
	}
	
	public static String INVALID_RESOURCE_LOCATION(String location) {
		return format("INVALID_RESOURCE_LOCATION", location);
	}
	
	public static String MEDIA_TYPE_UNKNOWN(String pathname) {
		return format("MEDIA_TYPE_UNKNOWN", pathname);
	}

	public static String MEDIA_TYPE_INVALID(String value) {
		return format("MEDIA_TYPE_INVALID", value);
	}
	
	// core.xml package
	
	public static String XML_ELEMENT_MISSING(String fileName, String parentName, Set<String> missing) {
		return format("XML_ELEMENT_MISSING", fileName, element(parentName), elements(missing));
	}

	public static String XML_ELEMENT_UNEXPECTED(String fileName, String actualName, String expectedName) {
		return format("XML_ELEMENT_UNEXPECTED", fileName, element(actualName), element(expectedName));
	}
	
	public static String XML_ATTRIBUTE_MISSING(String fileName, String elementName, String attributeName) {
		return format("XML_ATTRIBUTE_MISSING", fileName, element(elementName), attribute(attributeName));
	}

	public static String XML_ATTRIBUTE_EMPTY(String fileName, String elementName, String attributeName) {
		return format("XML_ATTRIBUTE_EMPTY", fileName, element(elementName), attribute(attributeName));
	}

	private static String element(String name) {
		String pattern = bundle.getString("xml.element");
		return MessageFormat.format(pattern, name);
	}

	private static String attribute(String name) {
		String pattern = bundle.getString("xml.attribute");
		return MessageFormat.format(pattern, name);
	}
	
	private static String elements(Set<String> elements) {
		String separator = bundle.getString("xml.element.separator");
		return elements.stream().map(Messages::element).collect(Collectors.joining(separator));
	}
	
	// core.zip package
	
	public static String END_OF_CENTRAL_DIRECTORY_NOT_FOUND() {
		return format("END_OF_CENTRAL_DIRECTORY_NOT_FOUND");
	}

	public static String CENTRAL_DIRECTORY_ENTRY_BROKEN() {
		return format("CENTRAL_DIRECTORY_ENTRY_BROKEN");
	}
	
	public static String ZIP_ENTRY_NOT_FOUND(String entryName, Path zipName) {
		return format("ZIP_ENTRY_NOT_FOUND", entryName, zipName);
	}
	
	/**
	 * Formats a message.
	 * @param key the key in properties file.
	 * @param arguments the arguments of the message.
	 * @return formatted message.
	 */
	private static String format(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		if (pattern == null) {
			return "";
		}
		return MessageFormat.format(pattern, arguments);
	}
}
