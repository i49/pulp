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

package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * A resource that contains content or instructions that contribute to
 * the logic and rendering of a {@link Publication}.
 */
public interface PublicationResource {

	/**
	 * Returns the location of this resource, which is either inside or outside the EPUB container.
	 * If the location points inside the container, it is relative to the root directory
	 * of the container.
	 * 
	 * @return the location of this resource.
	 */
	URI getLocation();
	
	/**
	 * Returns whether this resource exists in the container or not.
	 * This method must return the opposite result to calling {@link #isRemote()}.
	 * 
	 * @return {@code true} if this resource exists in the container, {@code false} otherwise.
	 * @see #isRemote()
	 */
	boolean isLocal();
	
	/**
	 * Returns whether this resource exists outside of the container or not.
	 * This method must return the opposite result to calling {@link #isLocal()}.
	 * 
	 * @return {@code true} if this resource exists outside of the container, {@code false} otherwise.
	 * @see #isLocal()
	 */
	boolean isRemote();
	
	/**
	 * Returns the media type of this resource.
	 * 
	 * @return the media type of this resource.
	 */
	MediaType getMediaType();
	
	/**
	 * Returns whether this resource is Core Media Type Resource or not.
	 * This method must return the opposite result to calling {@link #isForeign()}.
	 * 
	 * @return {@code true} if this resource is Core Media Type Resource, {@code false} otherwise.
	 * @see #isForeign()
	 */
	boolean isNative();
	
	/**
	 * Returns whether this resource is Foreign Resource or not.
	 * A publication resource is <i>foreign</i> if it is not Core Media Type Resource.
	 * This method must return the opposite result to calling {@link #isNative()}.
	 * 
	 * @return {@code true} if this resource is Foreign Resource, {@code false} otherwise.
	 * @see #isNative()
	 */
	boolean isForeign();
	
	/**
	 * Returns the content source for this resource.
	 * 
	 * @return the content source for this resource, never be {@code null}.
	 */
	ContentSource getContentSource();
	
	/**
	 * Assigns the content source that will provide initial content for this resource.
	 * 
	 * @param source the content source for this resource, cannot be {@code null}.
	 * @throws IllegalArgumentException if source is {@code null}.
	 */
	void setContentSource(ContentSource source);
	
	/**
	 * Opens a new {@link InputStream} that provides the content of this resource. 
	 * 
	 * @return a new input stream, which should be closed by the caller of this method.
	 * @throws IOException if an I/O error has occurred.
	 */
	InputStream openContent() throws IOException;
	
	/**
	 * Returns the content of this resource.
	 * 
	 * @return the content of this resource as a byte array.
	 * @throws IOException if an I/O error has occurred.
	 */
	byte[] getContent() throws IOException;
}
