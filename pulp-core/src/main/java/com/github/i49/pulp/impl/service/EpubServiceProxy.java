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

import java.net.URI;

import com.github.i49.pulp.api.metadata.TermRegistry;
import com.github.i49.pulp.api.publication.EpubService;
import com.github.i49.pulp.api.publication.Publication;
import com.github.i49.pulp.api.publication.PublicationReaderFactory;
import com.github.i49.pulp.api.publication.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.publication.PublicationWriterFactory;

/**
 * The Proxy of {@link EpubService} implementation.
 * This class will be instantiated per a thread.
 * All these instances bypass every method invocation to 
 * the shared single instance of {@link DefaultEpubService}.
 */
public class EpubServiceProxy implements EpubService {

	// The singleton of {@link EpubServiceProviderImpl} shared by all threads.
	private static final EpubService SINGLETON = new DefaultEpubService();
	
	public EpubServiceProxy() {
	}

	@Override
	public Publication createPublication() {
		return SINGLETON.createPublication();
	}

	@Override
	public PublicationReaderFactory createReaderFactory() {
		return SINGLETON.createReaderFactory();
	}

	@Override
	public PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		return SINGLETON.createResourceBuilderFactory(baseURI);
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return SINGLETON.createWriterFactory();
	}

	@Override
	public TermRegistry getPropertyTermRegistry() {
		return SINGLETON.getPropertyTermRegistry();
	}
}
