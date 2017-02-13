package com.github.i49.pulp.core;

import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;

/**
 * An implementation of {@link PublicationResourceBuilderFactory}.
 */
class PublicationResourceBuilderFactoryImpl implements PublicationResourceBuilderFactory {
	
	private final Map<String, PublicationResource> resourceMap;
	private final XmlService xmlService;
	
	/**
	 * Constructs this factory.
	 * @param resourceMap the map holding all resources built.
	 * @param xmlService the XML processing service.
	 */
	PublicationResourceBuilderFactoryImpl(Map<String, PublicationResource> resourceMap, XmlService xmlService) {
		this.resourceMap = resourceMap;
		this.xmlService = xmlService;
	}

	@Override
	public PublicationResourceBuilder builder(String pathname) {
		if (pathname == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("pathname"));
		} else if (resourceMap.containsKey(pathname)) {
			throw new EpubException(Messages.RESOURCE_ALREADY_EXISTS(pathname));
		}
		return new PublicationResourceBuilderImpl(pathname, this.xmlService);
	}

	/**
	 * An implementation of {@link PublicationResourceBuilder}.
	 */
	private class PublicationResourceBuilderImpl extends AbstractPublicationResourceBuilder {

		private PublicationResourceBuilderImpl(String pathname, XmlService xmlService) {
			super(pathname, xmlService);
		}

		@Override
		protected void addResource(PublicationResource resource) {
			resourceMap.put(resource.getPathname(), resource);
		}
	}
}
