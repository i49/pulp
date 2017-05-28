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

package com.github.i49.pulp.api.metadata;

import java.util.Optional;

import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;

/**
 * The release identifier to distinguish each version of the EPUB rendition.
 */
public interface ReleaseIdentifier {
	
	/**
	 * Returns the unique identifier of the rendition.
	 * 
	 * @return the unique identifier of the rendition, may be empty.
	 */
	Optional<Identifier> getUniqueIdentifier();
	
	/**
	 * Returns the last modification time of the rendition.
	 * 
	 * @return the last modification time of the rendition, may be empty.
	 */
	Optional<Modified> getLastModificationDate();
	
	/**
	 * Returns the string representation of this release identifier.
	 * <p>
	 * The value will be constructed with the unique identifier
	 * followed by the last modification time using the at sign as the separator
	 * of them. 
	 * </p>
	 * <p>
	 * e.g. "urn:uuid:A1B0D67E-2E81-4DF5-9E67-A64CBE366809@2011-01-01T12:00:00Z"
	 * </p>
	 * 
	 * @return the string representation of this release identifier, never be {@code null}.
	 */
	@Override
	String toString();
	
	/**
	 * Returns a hash code value computed based on the string 
	 * representation of this release identifier returned by {@link #toString()}.
	 * 
	 * @return a hash code value for this release identifier.
	 */
	@Override
	int hashCode();
	
	/**
	 * Compares two release identifiers based on the string representation
	 * of the each returned by {@link #toString()}. 
	 * 
	 * @param object the reference object with which to compare.
	 * @return {@code true} if this identifier equals the {@code object}, {@code false} otherwise.
	 */
	@Override
	boolean equals(Object object);
}
