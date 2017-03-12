package com.github.i49.pulp.core.xml;

import java.text.MessageFormat;
import java.util.ResourceBundle;

enum Message {

	XML_ELEMENT_MISSING,
	XML_ELEMENT_UNEXPECTED,
	XML_ATTRIBUTE_MISSING,
	XML_ATTRIBUTE_EMPTY
	;

	private static final String BASE_NAME = Message.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME);

	public String format(Object... args) {
		return MessageFormat.format(bundle.getString(name()), args);
	}
}
