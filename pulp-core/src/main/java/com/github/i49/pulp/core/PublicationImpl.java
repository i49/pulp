package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private static final String DEFAULT_RENDITION_LOCATION = "EPUB/package.opf";
	
	private final RootPublicationResourceRegistry resourceRegistry;
	private final List<Rendition> renditions = new ArrayList<>();
	
	public PublicationImpl(XmlService xmlService) {
		this.resourceRegistry = new RootPublicationResourceRegistry(xmlService);
	}

	@Override
	public Rendition getDefaultRendition() {
		if (renditions.size() == 0) {
			return null;
		}
		return renditions.get(0);
	}
	
	@Override
	public RootPublicationResourceRegistry getResourceRegistry() {
		return resourceRegistry;
	}
	
	@Override
	public Rendition addRendition() {
		return addRendition(DEFAULT_RENDITION_LOCATION);
	}
	
	@Override
	public Rendition addRendition(String location) {
		if (location == null) {
			location = DEFAULT_RENDITION_LOCATION;
		}
		Rendition rendition = new RenditionImpl(this, location);
		this.renditions.add(rendition);
		return rendition;
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableList(renditions).iterator();
	}
}
