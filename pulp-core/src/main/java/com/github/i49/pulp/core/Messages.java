package com.github.i49.pulp.core;

import java.net.URI;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_BASE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);
	
	static String PARAMETER_IS_NULL(String parameterName) {
		return localize("parameter.is.null", parameterName);
	}
	
	static String MISSING_PUBLICATION_RESOURCE(URI identifier) {
		return localize("missing.publication.resource", identifier);
	}
	
	static String RESOURCE_ALREADY_EXISTS(String pathname) {
		return localize("resource.already.exists", pathname);
	}
	
	static String INVALID_RESOURCE(URI identifier) {
		return localize("invalid.resource", identifier);
	}
	
	static String INVALID_PATHNAME(String pathname) {
		return localize("invalid.pathname", pathname);
	}
	
	static String UNKNOWN_MEDIA_TYPE(String pathname) {
		return localize("unknown.media.type", pathname);
	}
	
	static private String localize(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		if (pattern == null) {
			return null;
		}
		return MessageFormat.format(pattern, arguments);
	}
}
