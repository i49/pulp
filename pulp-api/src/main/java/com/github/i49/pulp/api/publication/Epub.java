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

package com.github.i49.pulp.api.publication;

import static com.github.i49.pulp.api.publication.EpubServiceLoader.getService;

import java.net.URI;
import java.nio.file.Path;

import com.github.i49.pulp.api.metadata.TermRegistry;

/**
 * Factory class for creating EPUB processing objects.
 * This class is the central interface of the API. 
 * 
 * <p>The following example shows how to create a new publication:</p>
 * <pre><code>
 * // Creates a new publication.
 * Publication publication = Epub.createPublication();
 * </code></pre>
 * 
 * <p>All the methods of this class are safe for use by multiple concurrent threads.</p>
 */
public final class Epub {

	/**
	 * Creates an empty publication.
	 * 
	 * @return an empty publication.
	 * @throws EpubException if API implementation was not found.
	 */
	public static Publication createPublication() {
		return getService().createPublication();
	}
	
	/**
	 * Creates an instance of {@link PublicationReader} that can read a publication from the given input stream.
	 * 
	 * @param path the location where the file to be read is located.
	 * @return created publication reader.
	 * @throws IllegalArgumentException if given {@code path} is {@code null}.
	 * @throws EpubException if API implementation was not found.
	 */
	public static PublicationReader createReader(Path path) {
		return createReaderFactory().createReader(path);
	}
	
	/**
	 * Creates an instance of {@link PublicationReaderFactory} that can produce one or more {@link PublicationReader}s.
	 * 
	 * @return created publication reader factory.
	 * @throws EpubException if API implementation was not found.
	 */
	public static PublicationReaderFactory createReaderFactory() {
		return getService().createReaderFactory();
	}

	/**
	 * Creates an instance of {@link PublicationResourceBuilderFactory} that can be used to create {@link PublicationResourceBuilder}.
	 *
	 * @param baseURI the base location to be used by the builders, must be a valid local location.
	 * @return created publication resource builder factory.
	 * @throws IllegalArgumentException if given {@code baseURI} is not a valid local location or {@code null}.
	 * @throws EpubException if API implementation was not found.
	 */
	public static PublicationResourceBuilderFactory createResourceBuilderFactory(URI baseURI) {
		return getService().createResourceBuilderFactory(baseURI);
	}
	
	/**
	 * Creates an instance of {@link PublicationWriter} that can write a publication to the given output stream.
	 * 
	 * @param path the location where a publication will be stored by the writer.
	 * @return created publication writer.
	 * @throws IllegalArgumentException if given {@code path} is {@code null}.
	 * @throws EpubException if API implementation was not found.
	 */
	public static PublicationWriter createWriter(Path path) {
		return createWriterFactory().createWriter(path);
	}
	
	/**
	 * Creates an instance of {@link PublicationWriterFactory} that can produce one or more {@link PublicationWriter}s.
	 * 
	 * @return created publication writer factory.
	 * @throws EpubException if API implementation was not found.
	 */
	public static PublicationWriterFactory createWriterFactory() {
		return getService().createWriterFactory();
	}

	public static TermRegistry getPropertyTermRegistry() {
		return getService().getPropertyTermRegistry();
	}
	
	private Epub() {}
}
