package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private final XmlService xmlService;
	private final List<Rendition> renditions;
	private final Map<String, PublicationResourceBuilderFactoryImpl> factories;
	
	public PublicationImpl(XmlService xmlService) {
		this.xmlService = xmlService;
		this.renditions = new ArrayList<>();
		this.factories = new HashMap<>();
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
		Rendition rendition = new RenditionImpl(prefix, getResourceBuilderFactory(prefix));
		this.renditions.add(rendition);
		return rendition;
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableList(renditions).iterator();
	}
	
	private PublicationResourceBuilderFactoryImpl getResourceBuilderFactory(String prefix) {
		PublicationResourceBuilderFactoryImpl factory = this.factories.get(prefix);
		if (factory == null) {
			factory = new PublicationResourceBuilderFactoryImpl(xmlService);
			this.factories.put(prefix, factory);
		}
		return factory;
	}
}
