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
import java.net.URISyntaxException;

import com.github.i49.pulp.impl.Messages;

/**
 * A location of a publication resource.
 * 
 * <p>There are two types of locations this class can handle.</p>
 * <ol>
 * <li>Absolute URI with explicit scheme.</li>
 * <li>URI relative to the root of the ZIP container.</li>
 * </ol>
 */
class PublicationResourceLocation {
	
	private final URI uri;
	
	/**
	 * Creates a new instance that points to the specified location.
	 * 
	 * @param location the URI of the resource represented by a string.
	 * @return created new instance.
	 * @throws IllegalArgumentException if specified location is not valid.
	 */
	public static PublicationResourceLocation of(String location) {
		try {
			URI uri = new URI(location);
			return of(uri);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(Messages.RESOURCE_LOCATION_INVALID(location), e);
		}
	}

	/**
	 * Creates a new instance that points to the specified URI.
	 * 
	 * @param location the URI of the resource.
	 * @return created new instance.
	 * @throws IllegalArgumentException if specified location is not valid.
	 */
	public static PublicationResourceLocation of(URI location) {
		if (!validateLocation(location)) {
			throw new IllegalArgumentException(Messages.RESOURCE_LOCATION_INVALID(location.toString()));
		}
		return new PublicationResourceLocation(location);
	}
	
	/**
	 * Creates a new instance that points to the location inside the EPUB container.
	 * 
	 * @param location the URI of the resource represented by a string.
	 * @return created new instance.
	 * @throws IllegalArgumentException if specified location is not a valid local location.
	 */
	public static PublicationResourceLocation ofLocal(String location) {
		try {
			URI uri = new URI(location);
			return ofLocal(uri);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(Messages.RESOURCE_LOCATION_INVALID(location), e);
		}
	}
	
	/**
	 * Creates a new instance that points to the location inside the EPUB container.
	 * 
	 * @param location the URI of the resource.
	 * @return created new instance.
	 * @throws IllegalArgumentException if specified location is not a valid local location.
	 */
	public static PublicationResourceLocation ofLocal(URI location) {
		if (location.isAbsolute()) {
			throw new IllegalArgumentException(Messages.RESOURCE_LOCATION_NOT_LOCAL(location));
		} else {
			return of(location);
		}
	}
	
	/**
	 * Returns whether this location points inside the EPUB container or not.
	 * 
	 * @return {@code true} if this location points inside the EPUB container, {@code false} otherwise.
	 */
	public boolean isLocal() {
		return !isRemote();
	}
	
	/**
	 * Returns whether this location points outside the EPUB container or not.
	 * 
	 * @return {@code true} if this location points outside the EPUB container, {@code false} otherwise.
	 */
	public boolean isRemote() {
		return uri.isAbsolute();
	}
	
	public URI resolve(String uri) {
		return this.uri.resolve(uri);
	}
	
	public URI relativize(URI uri) {
		if (uri.isAbsolute()) {
			return uri;
		}
		URI thisURI = this.uri.resolve("."); 
		URI thatURI = uri.normalize();
		
		String thisPath = thisURI.getPath();
		String thatPath = thatURI.getPath();
		
		String[] thisParts = thisPath.split("/");
		String[] thatParts = thatPath.split("/");
		
		int i = 0;
		while (i < thisParts.length && i < thatParts.length && thisParts[i].equals(thatParts[i])) {
			i++;
		}
		
		StringBuilder b = new StringBuilder();
		for (int j = i; j < thisParts.length; j++) {
			b.append("../");
		}
		for (int j = i; j < thatParts.length; j++) {
			if (j != i) {
				b.append("/");
			}
			b.append(thatParts[j]);
		}
		
		return URI.create(b.toString());
	}
	
	public URI toURI() {
		return uri;
	}

	@Override
	public String toString() {
		return uri.toString();
	}
	
	/**
	 * Constructs this location.
	 * 
	 * @param uri the URI representing this location, must be valid.
	 */
	private PublicationResourceLocation(URI uri) {
		this.uri= uri;
	}
	
	/**
	 * Validates the location of resources.
	 * 
	 * @param uri the location of the resource.
	 * @return {@code true} if specified location is a valid location.
	 */
	private static boolean validateLocation(URI uri) {
		if (uri.isAbsolute()) {
			return validateRemoteLocation(uri);
		} else {
			return validateLocalLocation(uri);
		}
	}
	
	/**
	 * Validates the remote location of resources.
	 * 
	 * @param uri the location of the resource.
	 * @return {@code true} if specified location is a valid remote location.
	 */
	private static boolean validateRemoteLocation(URI uri) {
		return !uri.isOpaque();
	}
	
	/**
	 * Validates the local location of resources.
	 * 
	 * @param uri the location of the resource.
	 * @return {@code true} if specified location is a valid local location.
	 */
	private static boolean validateLocalLocation(URI uri) {
		String path = uri.getPath();
		for (String name: path.split("/")) {
			if (name.isEmpty() || name.endsWith(".")) {
				return false;
			}
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (c == '\u0022' || c == '\u002A' || c == '\u003A' || c == '\u003C' ||
					c == '\u003E' || c == '\u003F' || c == '\\' || c == '\u007F' ||
					c <= '\u001F' ||  // C0 range
					('\u0080' <= c && c <= '\u009F') || // C1 range
					('\uE000' <= c && c <= '\uF8FF') || // Private Use Area
					('\uFDD0' <= c && c <= '\uFDEF') || // Non characters in Arabic Presentation Forms-A
					('\uFFF0' <= c && c <= '\uFFFF')) { // Specials
					return false;
				}
			}
		}
		return true;
	}
}
