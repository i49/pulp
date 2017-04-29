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
import com.github.i49.pulp.api.metadata.PropertyFactory;
import com.github.i49.pulp.api.spi.EpubServiceProvider;
import com.github.i49.pulp.impl.container.PublicationReaderFactoryImpl;
import com.github.i49.pulp.impl.container.PublicationWriterFactoryImpl;
import com.github.i49.pulp.impl.metadata.DefaultPropertyFactory;
import com.github.i49.pulp.impl.metadata.MetadataFactory;
import com.github.i49.pulp.impl.publication.MediaTypeRegistry;
import com.github.i49.pulp.impl.publication.PublicationImpl;
import com.github.i49.pulp.impl.publication.PublicationResourceBuilderFactoryImpl;

/**
 * The default implementation of {@link EpubServiceProvider} interface.
 */
class DefaultEpubServiceProvider implements EpubServiceProvider {
	
	private final MediaTypeRegistry typeRegistry = new MediaTypeRegistry();
	private final PropertyFactory propertyFactory = new DefaultPropertyFactory();
	private final MetadataFactory metadataFactory;
	
	DefaultEpubServiceProvider() {
		this.metadataFactory = new MetadataFactory(propertyFactory);
	}
	
	@Override
	public Publication createPublication() {
		return new PublicationImpl(this.metadataFactory);
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
	public PropertyFactory createPropertyFactory() {
		return this.propertyFactory;
	}
	
	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		checkNotNull(baseURI, "baseURI");
		return new PublicationResourceBuilderFactoryImpl(baseURI, typeRegistry);
	}
}
