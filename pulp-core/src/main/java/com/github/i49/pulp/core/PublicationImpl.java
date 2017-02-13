package com.github.i49.pulp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private final XmlService xmlService;
	private final List<Rendition> renditions;
	private final Map<String, Map<String, PublicationResource>> resources;
	
	public PublicationImpl(XmlService xmlService) {
		this.xmlService = xmlService;
		this.renditions = new ArrayList<>();
		this.resources = new HashMap<>();
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
		Map<String, PublicationResource> resourceMap = Collections.unmodifiableMap(getResourceMap(prefix));
		Rendition rendition = new RenditionImpl(prefix, resourceMap);
		this.renditions.add(rendition);
		return rendition;
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableList(renditions).iterator();
	}
	
	@Override
	public PublicationResourceBuilderFactoryImpl getResourceBuilderFactory(Rendition rendition) {
		if (rendition == null) {
			throw new NullPointerException(Messages.PARAMETER_IS_NULL("rendition"));
		}
		String prefix = rendition.getPrefix();
		Map<String, PublicationResource> resourceMap = getResourceMap(prefix);
		return new PublicationResourceBuilderFactoryImpl(resourceMap, xmlService);
	}
	
	private Map<String, PublicationResource> getResourceMap(String prefix) {
		Map<String, PublicationResource> resourceMap = this.resources.get(prefix);
		if (resourceMap == null) {
			resourceMap = new HashMap<>();
			this.resources.put(prefix, resourceMap);
		}
		return resourceMap;
	}
}
