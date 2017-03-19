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

package com.github.i49.pulp.core.publication;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.core.Messages;

/**
 * The registry that maintains all resources used by a publication. 
 */
class PublicationResourceRegistry {

	private final Set<PublicationResource> resources;
	private final Map<URI, PublicationResource> locationMap;
	
	/**
	 * Consructs this registry.
	 */
	public PublicationResourceRegistry() {
		this.resources = new HashSet<>();
		this.locationMap = new HashMap<>();
	}
	
	/**
	 * Returns the number of resources registered with this registry.
	 * @return the number of resources.
	 */
	public int getNumberOfResources() {
		return resources.size();
	}
	
	/**
	 * Returns {@code true} if this registry contains the resource at the specified location. 
	 * @param location the location of the resource.
	 * @return {@code true} if it contains the resource, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code location} is {@code null}.
	 */
	public boolean contains(URI location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		return locationMap.containsKey(location);
	}
	
	/**
	 * Returns {@code true} if this registry contains the resource specified. 
	 * @param resource the resource to find.
	 * @return {@code true} if it contains the resource, {@code false} otherwise.
	 * @throws IllegalArgumentException if {@code resource} is {@code null}.
	 */
	public boolean contains(PublicationResource resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null");
		}
		return resources.contains(resource);
	}
	
	/**
	 * Returns the resource specified by its location.
	 * @param location the location of the resource, cannot be {@code null}.
	 * @return found resource, never be {@code null}.
	 * @throws IllegalArgumentException if specified {@code location} is {@code null}.
	 * @throws NoSuchElementException if this registry does not contains the resource.
	 */
	public PublicationResource get(URI location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		PublicationResource resource = locationMap.get(location);
		if (resource == null) {
			throw new NoSuchElementException(Messages.RESOURCE_MISSING(location));
		}
		return resource;
	}

	/**
	 * Finds the resource specified by its location in this registry.
	 * @param location the location of the resource, cannot be {@code null}.
	 * @return the resource if this registry contains it, {@code null} otherwise.
	 * @throws IllegalArgumentException if specified {@code location} is {@code null}.
	 */
	public Optional<PublicationResource> find(URI location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null");
		}
		return Optional.ofNullable(locationMap.get(location));
	}
	
	/**
	 * Registers the resource with this registry.
	 * @param resource the publication resource to be registered.
	 * @throws IllegalArgumentException if specified {@code resource} is {@code null}.
	 */
	public void register(PublicationResource resource) {
		if (resource == null) {
			throw new IllegalArgumentException("resource is null");
		}
		if (contains(resource)) {
			return;
		}
		URI location = resource.getLocation();
		PublicationResource found = locationMap.get(location);
		if (found != null) {
			throw new EpubException(Messages.RESOURCE_ALREADY_EXISTS(location));
		}
		resources.add(resource);
		locationMap.put(location, resource);
	}
	
	public Set<PublicationResource> getAllResources() {
		return Collections.unmodifiableSet(resources); 
	}
}
