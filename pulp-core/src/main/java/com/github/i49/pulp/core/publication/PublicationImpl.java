package com.github.i49.pulp.core.publication;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private static final URI DEFAULT_RENDITION_LOCATION = URI.create("EPUB/package.opf");

	private final MediaTypeRegistry typeRegistry;
	private final Map<URI, PublicationResource> resourceMap;
	private final PublicationResourceRegistryImpl resourceRegistry;
	// renditions.
	private final HashMap<URI, Rendition> renditions = new LinkedHashMap<>();

	public PublicationImpl(MediaTypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
		this.resourceMap = new HashMap<>();
		this.resourceRegistry = createResourceRegistry(URI.create(""));
	}

	@Override
	public int getNumberOfRenditions() {
		return renditions.size();
	}
	
	@Override
	public Rendition getDefaultRendition() {
		if (getNumberOfRenditions() == 0) {
			return null;
		}
		return renditions.values().iterator().next();
	}
	
	@Override
	public PublicationResourceRegistry getResourceRegistry() {
		return resourceRegistry;
	}
	
	@Override
	public Rendition addRendition() {
		return addRendition(DEFAULT_RENDITION_LOCATION);
	}
	
	@Override
	public Rendition addRendition(String location) {
		URI uri = DEFAULT_RENDITION_LOCATION;
		if (location != null) {
			uri = URI.create(location);
			if (uri == null || !resourceRegistry.validateLocalLocation(uri)) {
				throw new EpubException(Messages.RESOURCE_LOCATION_INVALID(location));
			}
		}
		return addRendition(uri);
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableCollection(renditions.values()).iterator();
	}
	
	private Rendition addRendition(URI location) {
		if (this.renditions.containsKey(location)) {
			throw new EpubException(Messages.RENDITION_ALREADY_EXISTS(location));
		}
		PublicationResourceRegistry localRegistry = createResourceRegistry(location);
		Rendition rendition = new RenditionImpl(this, location, localRegistry);
		this.renditions.put(location, rendition);
		return rendition;
	}
	
	private PublicationResourceRegistryImpl createResourceRegistry(URI baseLocation) {
		return new PublicationResourceRegistryImpl(this.resourceMap, this.typeRegistry, baseLocation);
	}
}
