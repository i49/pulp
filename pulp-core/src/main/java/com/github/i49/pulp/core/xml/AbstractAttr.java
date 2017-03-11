package com.github.i49.pulp.core.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.TypeInfo;

/**
 * Blank implementation of {@link Attr}.
 */
public class AbstractAttr extends AbstractNode implements Attr {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean getSpecified() {
		return false;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public void setValue(String value) throws DOMException {
	}

	@Override
	public Element getOwnerElement() {
		return null;
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		return null;
	}

	@Override
	public boolean isId() {
		return false;
	}
}
