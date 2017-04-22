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

import java.net.URI;
import java.nio.file.Path;

import com.github.i49.pulp.api.PublicationResourceBuilder;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;

public class PublicationResourceBuilderFactoryImpl implements PublicationResourceBuilderFactory {
	
	private static final Path[] NO_PATHS = new Path[0];
	
	private final PublicationResourceLocation baseURI;
	private final MediaTypeRegistry typeRegistry;
	private Path[] sourcePath = NO_PATHS;
	
	public PublicationResourceBuilderFactoryImpl(URI baseURI, MediaTypeRegistry typeRegistry) {
		this.baseURI = PublicationResourceLocation.ofLocal(baseURI);
		this.typeRegistry = typeRegistry;
	}
	
	@Override
	public URI getBaseURI() {
		return baseURI.toURI();
	}
	
	@Override
	public void setSourcePath(Path... paths) {
		if (paths == null) {
			throw new IllegalArgumentException("\"paths\" must not be null");
		}
		for (Path path: paths) {
			if (path == null) {
				throw new IllegalArgumentException("\"paths\" cannot contain null");
			}
		}
		this.sourcePath = paths;
	}

	@Override
	public PublicationResourceBuilder newBuilder(String location) {
		if (location == null) {
			throw new IllegalArgumentException("\"location\" must not be null");
		}
		PublicationResourceLocation resolved = resolve(location);
		return new GenericPublicationResourceBuilder(resolved, location, this.sourcePath, this.typeRegistry);
	}
	
	/**
	 * Resolves the location of the publication resource to create.
	 * 
	 * @param location the relative location of the publication resource.
	 * @return resolved location.
	 */
	private PublicationResourceLocation resolve(String location) {
		URI uri = this.baseURI.resolve(location); 
		return PublicationResourceLocation.of(uri);
	}
}
