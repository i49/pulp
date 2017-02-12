package com.github.i49.pulp.core;

import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;

/**
 * An implementation of {@link PublicationResourceBuilderFactory}.
 */
class PublicationResourceBuilderFactoryImpl implements PublicationResourceBuilderFactory {
	
	private final XmlService xmlService;
	private Map<String, PublicationResource> resourceMap;
	
	PublicationResourceBuilderFactoryImpl(XmlService xmlService) {
		this.xmlService = xmlService;
		this.resourceMap = new HashMap<>();
	}

	@Override
	public PublicationResourceBuilder get(String pathname) {
		if (pathname == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("pathname"));
		} else if (getBuilt(pathname) != null) {
			throw new EpubException(Messages.RESOURCE_ALREADY_EXISTS(pathname));
		}
		return new PublicationResourceBuilderImpl(pathname, this.xmlService);
	}

	@Override
	public PublicationResource getBuilt(String pathname) {
		if (pathname == null) {
			return null;
		}
		return resourceMap.get(pathname);
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
