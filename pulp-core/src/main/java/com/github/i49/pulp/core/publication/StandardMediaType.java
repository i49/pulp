package com.github.i49.pulp.core.publication;

import com.github.i49.pulp.api.MediaType;

/**
 * Media types defined by IDPF.
 */
public enum StandardMediaType implements MediaType {
	
	APPLICATION_EPUB_ZIP("application", "epub+zip"),
	APPLICATION_OEBPS_PACKAGE_XML("application", "oebps-package+xml")
	;

	private final String type;
	private final String subtype;
	
	private StandardMediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSubtype() {
		return subtype;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getType() + "/" + getSubtype();
	}
}
