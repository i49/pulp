package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

public class PublicationImpl implements Publication {
	
	private final PublicationResourceManager resourceManager;
	private final List<Rendition> renditions;
	
	private static final String DEFAULT_RENDITION_PREFIX = "EPUB";
	
	public PublicationImpl(PublicationResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		this.renditions = new ArrayList<>();
	}

	@Override
	public Rendition getDefaultRendition() {
		if (renditions.size() == 0) {
			return null;
		}
		return renditions.get(0);
	}
	
	@Override
	public Rendition addRendition(String prefix) {
		if (prefix == null) {
			prefix = DEFAULT_RENDITION_PREFIX;
		}
		Rendition rendition = new RenditionImpl(prefix, resourceManager);
		this.renditions.add(rendition);
		return rendition;
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableList(renditions).iterator();
	}
}
