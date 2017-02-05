package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationBuilder;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.RenditionBuilder;

public class PublicationBuilderImpl implements PublicationBuilder {

	private static final String DEFAULT_BASENAME = "EPUB";
	
	private final ResourceFactory resourceFactory;
	private final List<Rendition> renditions = new ArrayList<>();
	
	public PublicationBuilderImpl(XmlService xmlService) {
		this.resourceFactory = new ResourceFactory(xmlService);
	}
	
	@Override
	public PublicationBuilder addRendition(Consumer<RenditionBuilder> c) {
		RenditionBuilderImpl b = new RenditionBuilderImpl(DEFAULT_BASENAME, this.resourceFactory);
		c.accept(b);
		this.renditions.add(b.build());
		return this;
	}
	
	public Publication build() {
		return new PublicationImpl(renditions, resourceFactory.getAllResources());
	}
	
	/**
	 * An implementation of {@link Publication}.
	 */
	private static class PublicationImpl implements Publication {

		private final List<Rendition> renditions;
		private final Map<String, PublicationResource> resources;

		public PublicationImpl(List<Rendition> renditions, Map<String, PublicationResource> resources) {
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
}
