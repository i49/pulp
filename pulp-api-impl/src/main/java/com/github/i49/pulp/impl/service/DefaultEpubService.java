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

package com.github.i49.pulp.impl.service;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.net.URI;

import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationReaderFactory;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.core.PublicationWriterFactory;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.spi.EpubService;
import com.github.i49.pulp.impl.io.readers.DefaultPublicationReaderFactory;
import com.github.i49.pulp.impl.io.writers.DefaultPublicationWriterFactory;
import com.github.i49.pulp.impl.metadata.DefaultMetadata;
import com.github.i49.pulp.impl.metadata.DefaultTermRegistry;
import com.github.i49.pulp.impl.publication.MediaTypeRegistry;
import com.github.i49.pulp.impl.publication.DefaultPublication;
import com.github.i49.pulp.impl.publication.DefaultPublicationResourceBuilderFactory;

/**
 * The default implementation of {@link EpubService} interface.
 */
class DefaultEpubService implements EpubService {
	
	private final MediaTypeRegistry typeRegistry = new MediaTypeRegistry();
	private final TermRegistry termRegistry = new DefaultTermRegistry();
	
	DefaultEpubService() {
	}
	
	@Override
	public PropertyFactory createPropertyFactory() {
		return null;
	}
	
	@Override
	public Publication createPublication() {
		return new DefaultPublication(this::createMetadata);
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return new DefaultPublicationReaderFactory(this);
	}
	
	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		checkNotNull(baseURI, "baseURI");
		return new DefaultPublicationResourceBuilderFactory(baseURI, this.typeRegistry);
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new DefaultPublicationWriterFactory();
	}

	@Override
	public TermRegistry getPropertyTermRegistry() {
		return termRegistry;
	}
	
	private Metadata createMetadata() {
		return new DefaultMetadata(this.termRegistry);
	}
}
