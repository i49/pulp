/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.core.publication;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link Publication}.
 */
public class PublicationImpl implements Publication {
	
	private static final URI DEFAULT_RENDITION_LOCATION = URI.create("EPUB/package.opf");

	private final PublicationResourceRegistry registry;
	// renditions.
	private final HashMap<URI, Rendition> renditions = new LinkedHashMap<>();

	public PublicationImpl() {
		this.registry = new PublicationResourceRegistry();
	}

	@Override
	public int getNumberOfRenditions() {
		return renditions.size();
	}
	
	@Override
	public Rendition getDefaultRendition() {
		if (getNumberOfRenditions() == 0) {
			return null;
		}
		return renditions.values().iterator().next();
	}
	
	@Override
	public Rendition addRendition() {
		return addRendition(DEFAULT_RENDITION_LOCATION);
	}
	
	@Override
	public Rendition addRendition(String location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		PublicationResourceLocation resourceLocation = PublicationResourceLocation.ofLocal(location);
		return addRendition(resourceLocation.toURI());
	}

	@Override
	public Set<PublicationResource> getAllResources() {
		return registry.getAllResources();
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableCollection(renditions.values()).iterator();
	}
	
	private Rendition addRendition(URI location) {
		if (this.renditions.containsKey(location)) {
			throw new EpubException(Messages.RENDITION_ALREADY_EXISTS(location.toString()));
		}
		Rendition rendition = new RenditionImpl(this, location, this.registry);
		this.renditions.put(location, rendition);
		return rendition;
	}
}