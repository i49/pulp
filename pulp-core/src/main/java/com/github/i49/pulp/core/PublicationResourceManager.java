package com.github.i49.pulp.core;

import java.util.HashMap;

import com.github.i49.pulp.api.PublicationResource;

public class PublicationResourceManager extends HashMap<String, PublicationResource> {

	private static final long serialVersionUID = 1L;

	private final XmlService xmlService;
	
	public PublicationResourceManager(XmlService xmlService) {
		this.xmlService = xmlService;
	}
	
	public XmlService getXmlService() {
		return xmlService;
	}
}
