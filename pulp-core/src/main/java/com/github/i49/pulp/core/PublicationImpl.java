package com.github.i49.pulp.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResourceRegistry;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private static final URI DEFAULT_RENDITION_LOCATION = URI.create("EPUB/package.opf");
	
	private final RootPublicationResourceRegistry resourceRegistry;
	private final List<Rendition> renditions = new ArrayList<>();
	
	public PublicationImpl() {
		this.resourceRegistry = new RootPublicationResourceRegistry();
	}

	@Override
	public Rendition getDefaultRendition() {
		if (renditions.size() == 0) {
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
			try {
				uri = new URI(null, null, location, null);
			} catch (URISyntaxException e) {
				throw new EpubException(Messages.INVALID_LOCATION(location), e);
			}
		}
		return addRendition(uri);
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableList(renditions).iterator();
	}
	
	private Rendition addRendition(URI location) {
		Rendition rendition = new RenditionImpl(this, location);
		this.renditions.add(rendition);
		return rendition;
	}
}
