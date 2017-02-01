package com.github.i49.pulp.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Spine;

/**
 * An implementation of {@link Publication}.
 */
class PublicationImpl implements Publication {

	private final Metadata metadata = new MetadataImpl();

	private final Map<String, PublicationResource> resourceMap = new HashMap<>();
	private final SpineImpl spine = new SpineImpl();
	
	private PublicationResource coverImage;
	
	PublicationImpl() {
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}
	
	@Override
	public boolean hasResource(String name) {
		return this.resourceMap.containsKey(name);
	}
	
	@Override
	public PublicationResource getResourceByName(String name) {
		if (name == null) {
			return null;
		}
		return resourceMap.get(name);
	}
	
	@Override
	public Collection<PublicationResource> getAllResources() {
		return Collections.unmodifiableCollection(this.resourceMap.values());
	}
	
	@Override
	public Publication addResource(PublicationResource resource) {
		if (resource != null) {
			PublicationResource previous = this.resourceMap.put(resource.getName(), resource);
			if (previous != null) {
				clearResource(previous);
			}
		}
		return this;
	}
	
	@Override
	public Publication removeResource(PublicationResource resource) {
		if (resource != null) {
			PublicationResource previous = this.resourceMap.remove(resource.getName());
			if (previous != null) {
				clearResource(previous);
			}
		}
		return this;
	}
	
	@Override
	public Spine getSpine() {
		return spine;
	}

	@Override
	public PublicationResource getCoverImage() {
		return coverImage;
	}

	@Override
	public Publication setCoverImage(String name) {
		if (name == null) {
			this.coverImage = null;
		} else {
			this.coverImage = getResourceByName(name);
		}
		return this;
	}
	
	private void clearResource(PublicationResource resource) {
		if (this.coverImage == resource) {
			this.coverImage = null;
		}
	}
}
