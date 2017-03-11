package com.github.i49.pulp.core.zip;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Messages for this package.
 */
enum Message {
	END_OF_CENTRAL_DIRECTORY_NOT_FOUND,
	CENTRAL_DIRECTORY_ENTRY_BROKEN,
	ZIP_ENTRY_NOT_FOUND
	;
	
	private static final String BASE_NAME = Message.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME);
	
	public String format(Object... args) {
		return MessageFormat.format(bundle.getString(name()), args);
	}
}
