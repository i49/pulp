package com.github.i49.pulp.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceRegistry;

/**
 * The root publication resource registry.
 */
class RootPublicationResourceRegistry implements PublicationResourceRegistry {

	private final Map<URI, PublicationResource> resourceMap;
	
	public RootPublicationResourceRegistry() {
		this.resourceMap = new HashMap<>();
	}
	
	private RootPublicationResourceRegistry(Map<URI, PublicationResource> resourceMap) {
		this.resourceMap = resourceMap;
	}

	@Override
	public boolean contains(String location) {
		if (location == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("location"));
		}
		return resourceMap.containsKey(resolve(location));
	}
	
	@Override
	public boolean contains(PublicationResource resource) {
		if (resource == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("resource"));
		}
		return resourceMap.containsValue(resource);
	}
	
	@Override
	public PublicationResource get(String location) {
		if (location == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("location"));
		}
		URI resolved = resolve(location);
		PublicationResource resource = resourceMap.get(resolved);
		if (resource == null) {
			throw new NoSuchElementException(Messages.MISSING_PUBLICATION_RESOURCE(resolved));
		}
		return resource;
	}

	@Override
	public Optional<PublicationResource> find(String location) {
		if (location == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("location"));
		}
		return Optional.ofNullable(resourceMap.get(resolve(location)));
	}
	
	@Override
	public int getNumberOfResources() {
		return resourceMap.size();
	}
	
	@Override
	public PublicationResourceBuilder builder(String location) {
		if (location == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("location"));
		}
		URI resolved = resolve(location);
		if (contains(location)) {
			throw new IllegalArgumentException(Messages.DUPLICATE_PUBLICATION_RESOURCE(resolved));
		}
		return new PublicationResourceBuilderImpl(resolved, location);
	}
	
	@Override
	public PublicationResourceRegistry getChildRegistry(String base) {
		if (base == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("base"));
		}
		try {
			URI baseURI = new URI(null, null, base, null);
			return new ChildPublicationResourceRegistry(resourceMap, baseURI);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(Messages.INVALID_LOCATION(base), e);
		}
	}
	
	/**
	 * Resolves the location against the base of this registry. 
	 * @param location the resource location that may be relative URI.
	 * @return the absolute location.
	 */
	protected URI resolve(String location) {
		return URI.create(location);
	}
	
	private void registreResource(PublicationResource resource) {
		resourceMap.put(resource.getLocation(), resource);
	}

	/**
	 * An implementation of {@link PublicationResourceBuilder}.
	 */
	private class PublicationResourceBuilderImpl extends AbstractPublicationResourceBuilder {

		private PublicationResourceBuilderImpl(URI identifier, String localPath) {
			super(identifier, localPath);
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
				URI base) {
			super(resourceMap);
			this.base = base;
		}
		
		@Override
		protected URI resolve(String location) {
			URI resolved = this.base.resolve(location); 
			return resolved;
		}
	}
}
