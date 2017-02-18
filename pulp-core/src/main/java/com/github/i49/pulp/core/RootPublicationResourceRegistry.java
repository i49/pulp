package com.github.i49.pulp.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceRegistry;

/**
 * The root publication resource registry.
 */
class RootPublicationResourceRegistry implements PublicationResourceRegistry {

	private final Map<URI, PublicationResource> resourceMap;
	private final XmlService xmlService;
	
	public RootPublicationResourceRegistry(XmlService xmlService) {
		this.resourceMap = new HashMap<>();
		this.xmlService = xmlService;
	}
	
	private RootPublicationResourceRegistry(Map<URI, PublicationResource> resourceMap, XmlService xmlService) {
		this.resourceMap = resourceMap;
		this.xmlService = xmlService;
	}

	@Override
	public boolean contains(String identifier) {
		if (identifier == null) {
			return false;
		}
		return resourceMap.containsKey(resolve(identifier));
	}
	
	@Override
	public boolean contains(PublicationResource resource) {
		if (resource == null) {
			return false;
		}
		return resourceMap.containsValue(resource);
	}
	
	@Override
	public PublicationResource get(String pathname) {
		if (pathname == null) {
			return null;
		}
		return resourceMap.get(resolve(pathname));
	}
	
	@Override
	public PublicationResourceBuilder builder(String identifier) {
		if (identifier == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("identifier"));
		} else if (contains(identifier)) {
			throw new EpubException(Messages.RESOURCE_ALREADY_EXISTS(identifier));
		}
		URI resolved = resolve(identifier);
		return new PublicationResourceBuilderImpl(resolved, identifier, this.xmlService);
	}
	
	@Override
	public PublicationResourceRegistry getChildRegistry(String base) {
		if (base == null) {
			return this;
		}
		try {
			URI baseURI = new URI(null, null, base, null);
			return new ChildPublicationResourceRegistry(resourceMap, xmlService, baseURI);
		} catch (URISyntaxException e) {
			throw new EpubException(Messages.INVALID_PATHNAME(base), e);
		}
	}
	
	/**
	 * Resolves the identifier against the base of this registry. 
	 * @param identifier the resource identifier that may be relative URI.
	 * @return the absolute URI.
	 */
	protected URI resolve(String identifier) {
		return URI.create(identifier);
	}
	
	private void registreResource(PublicationResource resource) {
		resourceMap.put(resource.getIdentifier(), resource);
	}

	/**
	 * An implementation of {@link PublicationResourceBuilder}.
	 */
	private class PublicationResourceBuilderImpl extends AbstractPublicationResourceBuilder {

		private PublicationResourceBuilderImpl(URI identifier, String localPath, XmlService xmlService) {
			super(identifier, localPath, xmlService);
		}

		@Override
		protected void addResource(PublicationResource resource) {
			registreResource(resource);
		}
	}
	
	/**
	 * Child resource registry.
	 */
	private static class ChildPublicationResourceRegistry extends RootPublicationResourceRegistry {
		
		private final URI base;

		public ChildPublicationResourceRegistry(
				Map<URI, PublicationResource> resourceMap, 
				XmlService xmlService,
				URI base) {
			super(resourceMap, xmlService);
			this.base = base;
		}
		
		@Override
		protected URI resolve(String pathname) {
			return this.base.resolve(pathname);
		}
	}
}
