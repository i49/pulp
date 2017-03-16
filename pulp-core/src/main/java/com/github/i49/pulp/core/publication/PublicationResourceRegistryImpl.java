package com.github.i49.pulp.core.publication;

import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link PublicationResourceRegistry}.
 */
class PublicationResourceRegistryImpl implements PublicationResourceRegistry {

	private final Map<URI, PublicationResource> resourceMap;
	private final URI baseLocation;
	private final MediaTypeRegistry typeRegistry;
	
	public PublicationResourceRegistryImpl(Map<URI, PublicationResource> resourceMap, MediaTypeRegistry typeRegistry, URI baseLocation) {
		this.resourceMap = resourceMap;
		this.typeRegistry = typeRegistry;
		this.baseLocation = baseLocation;
	}
	
	@Override
	public boolean contains(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		return resourceMap.containsKey(resolve(location));
	}
	
	@Override
	public boolean contains(PublicationResource resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null");
		}
		return resourceMap.containsValue(resource);
	}
	
	@Override
	public PublicationResource get(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		URI uri = resolve(location);
		PublicationResource resource = resourceMap.get(uri);
		if (resource == null) {
			throw new NoSuchElementException(Messages.MISSING_PUBLICATION_RESOURCE(uri));
		}
		return resource;
	}

	@Override
	public Optional<PublicationResource> find(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		return Optional.ofNullable(resourceMap.get(resolve(location)));
	}
	
	@Override
	public int getNumberOfResources() {
		return resourceMap.size();
	}
	
	@Override
	public Iterator<PublicationResource> iterator() {
		return Collections.unmodifiableMap(resourceMap).values().iterator();
	}

	@Override
	public PublicationResourceBuilder builder(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		URI uri = resolve(location);
		if (contains(location)) {
			throw new IllegalArgumentException(Messages.DUPLICATE_PUBLICATION_RESOURCE(uri));
		}
		return new PublicationResourceBuilderImpl(uri, location);
	}
	
	/**
	 * Validates the location of the local resource.
	 * @param location the location of the resource.
	 * @return {@code true} if the specified location is valid.
	 */
	public boolean validateLocalLocation(URI location) {
		if (location.isAbsolute()) {
			return false;
		}
		String path = location.getPath();
		for (String name: path.split("/")) {
			if (name.isEmpty() || name.endsWith(".")) {
				return false;
			}
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (c == '\u0022' || c == '\u002A' || c == '\u003A' || c == '\u003C' ||
					c == '\u003E' || c == '\u003F' || c == '\\' || c == '\u007F' ||
					c <= '\u001F' ||  // C0 range
					('\u0080' <= c && c <= '\u009F') || // C1 range
					('\uE000' <= c && c <= '\uF8FF') || // Private Use Area
					('\uFDD0' <= c && c <= '\uFDEF') || // Non characters in Arabic Presentation Forms-A
					('\uFFF0' <= c && c <= '\uFFFF')) { // Specials
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Resolves the specified location against the base URI of this registry.
	 * @param location the location to resolve.
	 * @return resolved location as a URI.
	 */
	private URI resolve(String location) {
		return this.baseLocation.resolve(location); 
	}
	
	private void registerResource(PublicationResource resource) {
		if (resource != null) {
			resourceMap.put(resource.getLocation(), resource);
		}
	}

	/**
	 * An implementation of {@link PublicationResourceBuilder}.
	 */
	private class PublicationResourceBuilderImpl extends AbstractPublicationResourceBuilder {

		private PublicationResourceBuilderImpl(URI location, String localPath) {
			super(location, localPath, typeRegistry);
		}

		@Override
		protected void addResource(PublicationResource resource) {
			registerResource(resource);
		}
	}
}
