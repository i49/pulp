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
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
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
	 * 
	 * @return the number of resources.
	 */
	public int getNumberOfResources() {
		return resourceSet.size();
	}

	/**
	 * Checks if this registry contains the resource at the specified location.
	 *  
	 * @param location the location of the resource, cannot be {@code null}.
	 * @return {@code true} if this registry contains the resource, {@code false} otherwise.
	 */
	public boolean contains(String location) {
		assert(location != null);
		try {
			return contains(new URI(location));
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(Messages.RESOURCE_LOCATION_INVALID(location), e);
		}
	}
	
	/**
	 * Checks if this registry contains the resource at the specified location.
	 *  
	 * @param location the location of the resource, cannot be {@code null}.
	 * @return {@code true} if this registry contains the resource, {@code false} otherwise.
	 */
	public boolean contains(URI location) {
		assert(location != null);
		return locationMap.containsKey(location);
	}
	
	/**
	 * Checks if this registry contains the resource specified. 
	 * @param resource the resource to check, cannot be {@code null}.
	 * @return {@code true} if this registry contains the resource, {@code false} otherwise.
	 */
	public boolean contains(PublicationResource resource) {
		assert(resource != null);
		return resourceSet.contains(resource);
	}

	/**
	 * Returns the resource specified by its location.
	 * 
	 * @param location the location relative to the root directory of the container, cannot be {@code null}.
	 * @return found resource, never be {@code null}.
	 * @throws NoSuchElementException if this registry does not contains the resource at the location.
	 */
	public PublicationResource get(String location) {
		assert(location != null);
		try {
			return get(new URI(location));
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(Messages.RESOURCE_LOCATION_INVALID(location), e);
		}
	}
	
	/**
	 * Returns the resource specified by its location.
	 * 
	 * @param location the location relative to the root directory of the container, cannot be {@code null}.
	 * @return found resource, never be {@code null}.
	 * @throws NoSuchElementException if this registry does not contains the resource at the location.
	 */
	public PublicationResource get(URI location) {
		assert(location != null);
		Entry entry = locationMap.get(location);
		if (entry == null) {
			throw new NoSuchElementException(Messages.RESOURCE_MISSING(location));
		}
		return entry.getResource();
	}

	/**
	 * Registers the resource with this registry.
	 * 
	 * @param resource the publication resource to be registered, cannot be {@code null}.
	 */
	public void register(PublicationResource resource) {
		assert(resource != null);
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
	 * 
	 * @param resource the resource to be removed, cannot be {@code null}.
	 */
	public void unregister(PublicationResource resource) {
		assert(resource != null);
		if (!resourceSet.contains(resource)) {
			return;
		}
		URI location = resource.getLocation();
		Entry entry = locationMap.get(location);
		if (entry.removeReference() == 0) {
			locationMap.remove(location);
			resourceSet.remove(resource);
		}
	}
	
	/**
	 * Returns all resources in this registry.
	 * 
	 * @return the set containing all resources. 
	 */
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
