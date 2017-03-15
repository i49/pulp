package com.github.i49.pulp.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private static final URI DEFAULT_RENDITION_LOCATION = URI.create("EPUB/package.opf");

	private final MediaTypeRegistry typeRegistry;
	private final Map<URI, PublicationResource> resourceMap;
	private final PublicationResourceRegistryImpl resourceRegistry;
	// a list of renditions.
	private final List<Rendition> renditions = new ArrayList<>();

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
		return renditions.get(0);
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
			if (uri == null || !resourceRegistry.validateResourceLocation(uri)) {
				throw new EpubException(Messages.INVALID_RESOURCE_LOCATION(location));
			}
		}
		return addRendition(uri);
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableList(renditions).iterator();
	}
	
	private Rendition addRendition(URI location) {
		PublicationResourceRegistry localRegistry = createResourceRegistry(location);
		Rendition rendition = new RenditionImpl(this, location, localRegistry);
		this.renditions.add(rendition);
		return rendition;
	}
	
	private PublicationResourceRegistryImpl createResourceRegistry(URI baseLocation) {
		return new PublicationResourceRegistryImpl(this.resourceMap, this.typeRegistry, baseLocation);
	}
}
