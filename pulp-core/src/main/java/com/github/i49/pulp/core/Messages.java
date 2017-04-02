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

	/* core.publication package */
	
	public static String RENDITION_ALREADY_EXISTS(String location) {
		return format("RENDITION_ALREADY_EXISTS", location);
	}
	
	public static String MANIFEST_ITEM_MISSING(String location) {
		return format("MANIFEST_ITEM_MISSING", location);
	}

	public static String MANIFEST_ITEM_ALREADY_EXISTS_IN_SPINE(URI location) {
		return format("MANIFEST_ITEM_ALREADY_EXISTS_IN_SPINE", location);
	}
	
	public static String RESOURCE_ALREADY_EXISTS_IN_PUBLICATION(URI location) {
		return format("RESOURCE_ALREADY_EXISTS_IN_PUBLICATION", location);
	}
	
	public static String RESOURCE_ALREADY_EXISTS_IN_MANIFEST(URI location) {
		return format("RESOURCE_ALREADY_EXISTS_IN_MANIFEST", location);
	}

	public static String RESOURCE_MISSING(URI location) {
		return format("RESOURCE_MISSING", location);
	}
	
	public static String RESOURCE_LOCATION_INVALID(String location) {
		return format("RESOURCE_LOCATION_INVALID", location);
	}

	public static String RESOURCE_LOCATION_NOT_LOCAL(URI location) {
		return format("RESOURCE_LOCATION_NOT_LOCAL", location);
	}
	
	public static String MEDIA_TYPE_NOT_DETECTED(String pathname) {
		return format("MEDIA_TYPE_NOT_DETECTED", pathname);
	}

	public static String MEDIA_TYPE_INVALID(String value) {
		return format("MEDIA_TYPE_INVALID", value);
	}
	
	/* core.container package */
	
	public static String CONTAINER_NOT_FOUND(Path path) {
		return format("CONTAINER_NOT_FOUND", path);
	}

	public static String CONTAINER_IO_FAILURE(Path path) {
		return format("CONTAINER_IO_FAILURE", path);
	}
	
	public static String CONTAINER_EMPTY(Path path) {
		return format("CONTAINER_EMPTY", path);
	}

	public static String CONTAINER_CORRUPT(Path path) {
		return format("CONTAINER_CORRUPT", path);
	}
	
	public static String CONTAINER_MIMETYPE_MISSING(Path path) {
		return format("CONTAINER_MIMETYPE_MISSING", path);
	}

	public static String CONTAINER_MIMETYPE_MISLOCATED(Path path) {
		return format("CONTAINER_MIMETYPE_MISLOCATED", path);
	}
	
	public static String CONTAINER_MIMETYPE_UNEXPECTED(Path path, String mimetype) {
		return format("CONTAINER_MIMETYPE_UNEXPECTED", path, mimetype);
	}
	
	public static String MANIFEST_ITEM_ID_MISSING(String id) {
		return format("MANIFEST_ITEM_ID_MISSING", id);
	}
	
	public static String XML_DOCUMENT_VERSION_UNSUPPORTED(String version) {
		return format("XML_DOCUMENT_VERSION_UNSUPPORTED", version);
	}
	
	public static String XML_DOCUMENT_NOT_WELL_FORMED() {
		return format("XML_DOCUMENT_NOT_WELL_FORMED");
	}
	
	/* core.xml package */
	
	public static String XML_PARSER_MISCONFIGURED() {
		return format("XML_PARSER_MISCONFIGURED");
	}
	
	public static String XML_TRANSFORMER_MISCONFIGURED() {
		return format("XML_TRANSFORMER_MISCONFIGURED");
	}

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
	
	/* core.zip package */
	
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
