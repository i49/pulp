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

package com.github.i49.pulp.api.spi;

import java.net.URI;

import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationReader;
import com.github.i49.pulp.api.PublicationReaderFactory;
import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.api.metadata.PropertyFactory;

/**
 * Service Provider Interface (SPI) to be implemented by API implementor.
 * This type is for internal use and the users of the API need not to care this interface.
 */
public interface EpubServiceProvider {
	
	/**
	 * Creates an empty publication.
	 * 
	 * @return an empty publication.
	 */
	Publication createPublication();

	/**
	 * Creates an instance of factory that can be used to produce {@link PublicationReader}.
	 * 
	 * @return an instance of publication reader factory.
	 */
	PublicationReaderFactory createReaderFactory();

	/**
	 * Creates an instance of factory that can be used to produce {@link PublicationWriter}.
	 * 
	 * @return an instance of publication writer factory.
	 */
	PublicationWriterFactory createWriterFactory();
	
	/**
	 * Creates an instance of factory that can be used to produce metadata properties.
	 * 
	 * @return an instance of metadata property factory.
	 */
	PropertyFactory createMetadataPropertyFactory();
	
	/**
	 * Creates an instance of factory that can be used to produce {@link PublicationResourceBuilder}.
	 * 
	 * @param baseURI the base location to be used by the builders.
	 * @return an instance of publication resource builder factory.
	 * @throws IllegalArgumentException if given {@code baseURI} is {@code null}.
	 */
	PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI);
}
