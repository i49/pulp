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

	private final Set<PublicationResource> resourceSet;
	private final Set<PublicationResource> immutableSet;
	private final Map<URI, Entry> locationMap;
	
	/**
	 * Constructs this registry.
	 */
	public PublicationResourceRegistry() {
		this.resourceSet = new HashSet<>();
		this.immutableSet = Collections.unmodifiableSet(this.resourceSet);
		this.locationMap = new HashMap<>();
	}
	
	/**
	 * Returns the number of resources registered with this registry.
	 * @return the number of resources.
	 */
	public int getNumberOfResources() {
		return resourceSet.size();
	}
	
	/**
	 * Returns {@code true} if this registry contains the resource at the specified location. 
	 * @param location the location of the resource, can be {@code null}.
	 * @return {@code true} if this registry contains the resource, {@code false} otherwise.
	 */
	public boolean contains(URI location) {
		if (location == null) {
			return false;
		}
		return locationMap.containsKey(location);
	}
	
	/**
	 * Returns {@code true} if this registry contains the resource specified. 
	 * @param resource the resource to find, can be {@code null}.
	 * @return {@code true} if this registry contains the resource, {@code false} otherwise.
	 */
	public boolean contains(PublicationResource resource) {
		if (resource == null) {
			return false;
		}
		return resourceSet.contains(resource);
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
		Entry entry = locationMap.get(location);
		if (entry == null) {
			throw new NoSuchElementException(Messages.RESOURCE_MISSING(location));
		}
		return entry.getResource();
	}

	/**
	 * Finds the resource specified by its location in this registry.
	 * @param location the location of the resource.
	 * @return the resource if this registry contains it, {@code null} otherwise.
	 */
	public Optional<PublicationResource> find(URI location) {
		if (location == null) {
			return Optional.empty();
		}
		Entry entry = locationMap.get(location);
		if (entry == null) {
			return Optional.empty();
		}
		return Optional.of(entry.getResource());
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
		URI location = resource.getLocation();
		Entry entry = locationMap.get(location);
		if (entry == null) {
			entry = new Entry(resource);
			locationMap.put(location, entry);
			resourceSet.add(resource);
		} else {
			if (entry.getResource() != resource) {
				throw new EpubException(Messages.RESOURCE_ALREADY_EXISTS_IN_PUBLICATION(location));
			}
			entry.addReference();
		}
	}
	
	/**
	 * Unregister a resource from this registry.
	 * @param resource the resource to be removed.
	 */
	public void unregister(PublicationResource resource) {
		if (resource == null || !resourceSet.contains(resource)) {
			return;
		}
		URI location = resource.getLocation();
		Entry entry = locationMap.get(location);
		if (entry.removeReference() == 0) {
			locationMap.remove(location);
			resourceSet.remove(resource);
		}
	}
	
	public Set<PublicationResource> getAllResources() {
		return immutableSet;
	}

	/**
	 * An entry of this registry.
	 */
	private static class Entry {
		
		private PublicationResource resource;
		private int usingRenditions;
		
		private Entry(PublicationResource resource) {
			this.resource = resource;
			this.usingRenditions = 1;
		}
		
		public PublicationResource getResource() {
			return resource;
		}
		
		public int addReference() {
			return ++usingRenditions;
		}

		public int removeReference() {
			return --usingRenditions;
		}
	}
}
