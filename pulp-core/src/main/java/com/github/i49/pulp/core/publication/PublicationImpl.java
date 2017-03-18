package com.github.i49.pulp.core.publication;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private static final URI DEFAULT_RENDITION_LOCATION = URI.create("EPUB/package.opf");

	private final PublicationResourceRegistry registry;
	// renditions.
	private final HashMap<URI, Rendition> renditions = new LinkedHashMap<>();

	public PublicationImpl() {
		this.registry = new PublicationResourceRegistry();
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
	public Rendition addRendition() {
		return addRendition(DEFAULT_RENDITION_LOCATION);
	}
	
	@Override
	public Rendition addRendition(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		PublicationResourceLocation resourceLocation = PublicationResourceLocation.ofLocal(location);
		return addRendition(resourceLocation.toURI());
	}

	@Override
	public Set<PublicationResource> getAllResources() {
		return registry.getAllResources();
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableCollection(renditions.values()).iterator();
	}
	
	private Rendition addRendition(URI location) {
		if (this.renditions.containsKey(location)) {
			throw new EpubException(Messages.RENDITION_ALREADY_EXISTS(location.toString()));
		}
		Rendition rendition = new RenditionImpl(this, location, this.registry);
		this.renditions.put(location, rendition);
		return rendition;
	}
}
