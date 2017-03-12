package com.github.i49.pulp.core.container;

import java.text.MessageFormat;
import java.util.ResourceBundle;

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

	MANIFEST_ITEM_NOT_FOUND
	;

	private static final String BASE_NAME = Message.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME);
	
	public String format(Object... args) {
		return MessageFormat.format(bundle.getString(name()), args);
	}
}
