package com.github.i49.pulp.core;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationBuilder;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;

public class PublicationBuilderImpl implements PublicationBuilder {

	private static final String DEFAULT_PREFIX = "EPUB";
	
	private final ResourceFactory resourceFactory;
	private final List<Rendition> renditions = new ArrayList<>();
	
	private boolean buildingRendition;

	private String prefix;
	private Map<String, RenditionImpl.ItemImpl> items;
	private List<RenditionImpl.PageImpl> pages;
	private RenditionImpl.ItemImpl coverImage;
	
	public PublicationBuilderImpl(XmlService xmlService) {
		this.resourceFactory = new ResourceFactory(xmlService);
		this.buildingRendition = false;
	}
	
	@Override
	public void startRendition() {
		startRendition(DEFAULT_PREFIX);
	}
	
	@Override
	public void startRendition(String prefix) {
		if (prefix == null) {
			throw new NullPointerException("prefix is null.");
		} else if (isBuildingRendition()) {
			throw new IllegalStateException();
		}
		
		this.prefix = prefix;
		this.items = new HashMap<>();
		this.pages = new ArrayList<>();
		this.coverImage = null;
		
		this.buildingRendition = true;
	}
	
	@Override
	public void endRendition() {
		if (isBuildingRendition()) {
			buildRendition();
			this.buildingRendition = false;
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public void addResource(String name, Path path) {
		PublicationResource resource = resourceFactory.addResource(prefix, name, path);
		addItem(name, resource);
	}

	@Override
	public void addResource(String name, URI uri) {
		PublicationResource resource = resourceFactory.addResource(prefix, name, uri);
		addItem(name, resource);
	}

	@Override
	public void addResource(String name, CoreMediaType mediaType, Path path) {
		PublicationResource resource = resourceFactory.addResource(prefix, name, mediaType, path);
		addItem(name, resource);
	}

	@Override
	public void addResource(String name, CoreMediaType mediaType, URI uri) {
		PublicationResource resource = resourceFactory.addResource(prefix, name, mediaType, uri);
		addItem(name, resource);
	}
	
	@Override
	public void selectCoverImage(String name) {
		RenditionImpl.ItemImpl item = items.get(name);
		if (item == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" is not found in the rendition.");
		}
		this.coverImage = item;
	}

	@Override
	public void addPage(String name) {
		addPage(name, true);
	}

	@Override
	public void addPage(String name, boolean linear) {
		RenditionImpl.ItemImpl item = items.get(name);
		if (item == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" is not found in the rendition.");
		}
		this.pages.add(new RenditionImpl.PageImpl(item, linear));
	}

	@Override
	public Publication build() {
		return new PublicationImpl(renditions, resourceFactory.getAllResources());
	}
	
	private boolean isBuildingRendition() {
		return buildingRendition;
	}

	private void addItem(String name, PublicationResource resource) {
		this.items.put(name, new RenditionImpl.ItemImpl(name, resource));
	}
	
	private Rendition buildRendition() {
		RenditionImpl rendition = new RenditionImpl(this.prefix, this.items, this.pages);
		rendition.setCoverImage(this.coverImage);
		this.renditions.add(rendition);
		return rendition;
	}
}
