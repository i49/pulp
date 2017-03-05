package com.github.i49.pulp.core;

import java.net.URI;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_BASE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);
	
	public static String NULL_PARAMETER(String parameterName) {
		return localize("null.parameter", parameterName);
	}
	
	public static String MISSING_PUBLICATION_RESOURCE(URI location) {
		return localize("missing.publication.resource", location);
	}
	
	public static String DUPLICATE_PUBLICATION_RESOURCE(URI location) {
		return localize("duplicate.publication.resource", location);
	}

	public static String MISSING_MANIFEST_ITEM(URI location) {
		return localize("missing.manifest.item", location);
	}
	
	public static String DUPLICATE_MANIFEST_ITEM(URI location) {
		return localize("duplicate.manifest.item", location);
	}
	
	public static String INVALID_RESOURCE(URI identifier) {
		return localize("invalid.resource", identifier);
	}
	
	public static String INVALID_LOCATION(String location) {
		return localize("invalid.location", location);
	}
	
	public static String MEDIA_TYPE_UNKNOWN(String pathname) {
		return localize("media.type.unknown", pathname);
	}

	public static String MEDIA_TYPE_INVALID(String value) {
		return localize("media.type.invalid", value);
	}
	
	private static String localize(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		if (pattern == null) {
			return null;
		}
		return MessageFormat.format(pattern, arguments);
	}
}
