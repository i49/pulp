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

package com.github.i49.pulp.core;

import java.net.URI;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.metadata.MetadataPropertyFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.core.container.PublicationReaderFactoryImpl;
import com.github.i49.pulp.core.container.PublicationWriterFactoryImpl;
import com.github.i49.pulp.core.metadata.MetadataPropertyFactoryImpl;
import com.github.i49.pulp.core.publication.MediaTypeRegistry;
import com.github.i49.pulp.core.publication.PublicationImpl;
import com.github.i49.pulp.core.publication.PublicationResourceBuilderFactoryImpl;

/**
 * An implementation of {@link EpubServiceProvider} interface.
 */
public class EpubServiceProviderImpl implements EpubServiceProvider {
	
	private final MediaTypeRegistry typeRegistry = new MediaTypeRegistry();
	
	@Override
	public Publication createPublication() {
		return new PublicationImpl();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return new PublicationReaderFactoryImpl(this);
	}
	
	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl();
	}

	@Override
	public MetadataPropertyFactory createMetadataPropertyFactory() {
		return new MetadataPropertyFactoryImpl();
	}
	
	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		if (baseURI == null) {
			throw new IllegalArgumentException("baseURI is null");
		}
		return new PublicationResourceBuilderFactoryImpl(baseURI, typeRegistry);
	}
}
