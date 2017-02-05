package com.github.i49.pulp.core;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.RenditionBuilder;
import com.github.i49.pulp.api.Spine;
import com.github.i49.pulp.api.SpineBuilder;

public class RenditionBuilderImpl implements RenditionBuilder {

	private final String baseName;
	private final Map<String, Rendition.Item> items;
	private Rendition.Item coverImage;

	private final ResourceFactory resourceFactory;
	private final SpineBuilderImpl spineBuilder;
	
	public RenditionBuilderImpl(String baseName, ResourceFactory resourceFactory) {
		this.baseName = baseName;
		this.resourceFactory = resourceFactory;
		this.items = new HashMap<>();
		this.spineBuilder = new SpineBuilderImpl(this.items);
	}
	
	@Override
	public RenditionBuilder addResource(String name, Path path) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, path);
		return addItem(name, resource);
	}

	@Override
	public RenditionBuilder addResource(String name, URI uri) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, uri);
		return addItem(name, resource);
	}

	@Override
	public RenditionBuilder addResource(String name, CoreMediaType mediaType, Path path) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, mediaType, path);
		return addItem(name, resource);
	}

	@Override
	public RenditionBuilder addResource(String name, CoreMediaType mediaType, URI uri) {
		PublicationResource resource = resourceFactory.addResource(baseName, name, mediaType, uri);
		return addItem(name, resource);
	}
	
	@Override
	public RenditionBuilder selectCoverImage(String name) {
		Rendition.Item item = items.get(name);
		if (item == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" is not found in the rendition.");
		}
		this.coverImage = item;
		return this;
	}

	@Override
	public SpineBuilder start(String name) {
		spineBuilder.next(name);
		return spineBuilder;
	}

	@Override
	public SpineBuilder start(String name, boolean linear) {
		spineBuilder.next(name, linear);
		return spineBuilder;
	}
	
	public Rendition build() {
		RenditionImpl rendition = new RenditionImpl(this.baseName, this.items);
		rendition.spine = this.spineBuilder.build();
		rendition.coverImage = this.coverImage;
		return rendition;
	}
	
	private RenditionBuilder addItem(String name, PublicationResource resource) {
		this.items.put(name, new ItemImpl(name, resource));
		return this;
	}
	
	private static class RenditionImpl implements Rendition {
		
		private final String baseName;
		private final MetadataImpl metadata = new MetadataImpl();
		private final Map<String, Rendition.Item> items;
		private Spine spine;
		private Item coverImage;
		
		public RenditionImpl(String baseName, Map<String, Rendition.Item> items) {
			this.baseName = baseName;
			this.items = Collections.unmodifiableMap(items);
		}

		@Override
		public Metadata getMetadata() {
			return metadata;
		}

		@Override
		public boolean hasItem(String name) {
			if (name == null) {
				return false;
			}
			return items.containsKey(name);
		}

		@Override
		public Item getItemByName(String name) {
			if (name == null) {
				return null;
			}
			return items.get(name);
		}

		@Override
		public Collection<Item> getAllItems() {
			return items.values();
		}

		@Override
		public Spine getSpine() {
			return spine;
		}

		@Override
		public Item getCoverImage() {
			return coverImage;
		}
	}
	
	private static class ItemImpl implements Rendition.Item {

		private final String name;
		private final PublicationResource resource;
		
		public ItemImpl(String name, PublicationResource resource) {
			this.name = name;
			this.resource = resource;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public PublicationResource getResource() {
			return resource;
		}

		@Override
		public CoreMediaType getMediaType() {
			return getResource().getMediaType();
		}
	}
}
