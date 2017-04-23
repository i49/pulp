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

/**
 * Proxy of {@link EpubServiceProvider} implementation.
 */
public class EpubServiceProviderProxy implements EpubServiceProvider {

	// The singleton of {@link EpubServiceProviderImpl} shared by all threads.
	private static final EpubServiceProvider singleton = new DefaultEpubServiceProvider();
	
	public EpubServiceProviderProxy() {
	}

	@Override
	public Publication createPublication() {
		return singleton.createPublication();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return singleton.createReaderFactory();
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return singleton.createWriterFactory();
	}

	@Override
	public PropertyFactory createMetadataPropertyFactory() {
		return singleton.createMetadataPropertyFactory();
	}
	
	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		return singleton.createResourceBuilderFactory(baseURI);
	}
}
