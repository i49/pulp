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

package com.github.i49.pulp.impl;

import java.net.URI;

import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationReaderFactory;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.core.PublicationWriterFactory;
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.impl.container.PublicationReaderFactoryImpl;
import com.github.i49.pulp.impl.container.PublicationWriterFactoryImpl;
import com.github.i49.pulp.impl.metadata.DefaultPropertyFactory;
import com.github.i49.pulp.impl.publication.MediaTypeRegistry;
import com.github.i49.pulp.impl.publication.PublicationImpl;
import com.github.i49.pulp.impl.publication.PublicationResourceBuilderFactoryImpl;

/**
 * The single implementation of {@link EpubServiceProvider} interface.
 */
public class DefaultEpubServiceProvider implements EpubServiceProvider {
	
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
	public PropertyFactory createMetadataPropertyFactory() {
		return new DefaultPropertyFactory();
	}
	
	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		if (baseURI == null) {
			throw new IllegalArgumentException("baseURI is null");
		}
		return new PublicationResourceBuilderFactoryImpl(baseURI, typeRegistry);
	}
}
