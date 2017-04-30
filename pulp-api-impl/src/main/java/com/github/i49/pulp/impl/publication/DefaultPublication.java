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

package com.github.i49.pulp.impl.publication;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link Publication}.
 */
public class DefaultPublication implements Publication {
	
	private static final PublicationResourceLocation DEFAULT_RENDITION_LOCATION = 
			PublicationResourceLocation.of("EPUB/package.opf");

	private final MetadataFactory metadataFactory;
	
	// The registry of publication resources.
	private final PublicationResourceRegistry registry;

	// Renditions.
	private final HashMap<URI, Rendition> renditions = new LinkedHashMap<>();

	public DefaultPublication(MetadataFactory metadataFactory) {
		this.metadataFactory = metadataFactory;
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
		return createRendition(DEFAULT_RENDITION_LOCATION);
	}
	
	@Override
	public Rendition addRendition(String location) {
		checkNotNull(location, "location");
		return createRendition(PublicationResourceLocation.ofLocal(location));
	}

	@Override
	public Set<PublicationResource> getAllResources() {
		return registry.getAllResources();
	}

	@Override
	public Iterator<Rendition> iterator() {
		return Collections.unmodifiableCollection(renditions.values()).iterator();
	}
	
	@Override
	public boolean containsResource(String location) {
		checkNotNull(location, "location");
		return this.registry.contains(location);
	}
	
	@Override
	public PublicationResource getResource(String location) {
		checkNotNull(location, "location");
		return this.registry.get(location);
	}
	
	private Rendition createRendition(PublicationResourceLocation location) {
		URI uri = location.toURI();
		if (this.renditions.containsKey(uri)) {
			throw new EpubException(Messages.RENDITION_ALREADY_EXISTS(location.toString()));
		}
		Metadata metadata = this.metadataFactory.newMetadata();
		Rendition rendition = new DefaultRendition(this, location, this.registry, metadata);
		this.renditions.put(uri, rendition);
		return rendition;
	}
}