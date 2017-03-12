package com.github.i49.pulp.core.xml;

import javax.xml.soap.Node;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.TypeInfo;

/**
 * Blank implementation of {@link Attr}.
 */
public class NominalAttr extends NominalNode implements Attr {

	public NominalAttr(String localName, String namespaceURI) {
		super(localName, namespaceURI);
	}
	
	@Override
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}
	
	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getSpecified() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(String value) throws DOMException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element getOwnerElement() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isId() {
		throw new UnsupportedOperationException();
	}
}
