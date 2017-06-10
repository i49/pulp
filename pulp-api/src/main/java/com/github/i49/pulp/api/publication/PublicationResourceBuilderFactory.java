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

import java.net.URI;
import java.nio.file.Path;

/**
 * The factory to be used to produce instances of {@link PublicationResourceBuilder}.
 */
public interface PublicationResourceBuilderFactory {

	/**
	 * Returns the base URI of publication resources.
	 * 
	 * @return the base URI of publication resources.
	 */
	URI getBaseURI();
	
	/**
	 * Specifies one or more paths from which resource builders search for the resource content.
	 * Passing no paths to this method disables the automatic searching of the content.  
	 * 
	 * @param paths zero or more paths for resource builders to search for the resource contents.
	 * By default no paths are assigned.
	 */
	void setSourcePath(Path... paths);
	
	/**
	 * Creates a builder for building a publication resource located at the specified location.
	 * 
	 * @param location the location of the publication resource relative to the base URI.
	 * @return created resource builder, never be {@code null}.
	 * @throws IllegalArgumentException if {@code location} is not a valid location or {@code null}.
	 */
	PublicationResourceBuilder newBuilder(String location);
}
