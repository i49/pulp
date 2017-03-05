package com.github.i49.pulp.core.container;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ResourceBundle;

final class Messages {

	private static final String BUNDLE_BASE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);

	static String CONTAINER_NOT_READABLE(Path path) {
		return localize("container.not.readable", path);
	}

	static String CONTAINER_NOT_WRITEABLE(Path path) {
		return localize("container.not.writeable", path);
	}
	
	private static String localize(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		if (pattern == null) {
			return null;
		}
		return MessageFormat.format(pattern, arguments);
	}
}
