package com.github.i49.pulp.core;

import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;

class PublicationImpl implements Publication {
	
	private final List<Rendition> renditions;
	@SuppressWarnings("unused")
	private final Map<String, PublicationResource> resources;
	
	PublicationImpl(List<Rendition> renditions, Map<String, PublicationResource> resources) {
		this.renditions = renditions;
		this.resources = resources;
	}

	@Override
	public Rendition getDefaultRendition() {
		if (renditions.size() == 0) {
			return null;
		}
		return renditions.get(0);
	}
}
