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

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.core.Messages;

/**
 * A location of a publication resource.
 */
class PublicationResourceLocation {
	
	private final URI uri;
	
	/**
	 * Creates a new instance that points to the specified location.
	 * @param location the URI of the resource.
	 * @return created new instance.
	 */
	public static PublicationResourceLocation of(URI location) {
		if (!validateLocation(location)) {
			throw new EpubException(Messages.RESOURCE_LOCATION_INVALID(location.toString()));
		}
		return new PublicationResourceLocation(location);
	}

	public static PublicationResourceLocation ofLocal(String location) {
		try {
			URI uri = new URI(location);
			if (uri.isAbsolute()) {
				throw new EpubException(Messages.RESOURCE_LOCATION_NOT_LOCAL(location));
			} else {
				return of(uri);
			}
		} catch (URISyntaxException e) {
			throw new EpubException(Messages.RESOURCE_LOCATION_INVALID(location), e);
		}
	}
	
	public boolean isLocal() {
		return !isRemote();
	}
	
	public boolean isRemote() {
		return uri.isAbsolute();
	}
	
	public URI toURI() {
		return uri;
	}

	@Override
	public String toString() {
		return uri.toString();
	}
	
	private PublicationResourceLocation(URI uri) {
		this.uri= uri;
	}
	
	private static boolean validateLocation(URI uri) {
		if (uri.isAbsolute()) {
			return validateRemoteLocation(uri);
		} else {
			return validateLocalLocation(uri);
		}
	}
	
	private static boolean validateRemoteLocation(URI uri) {
		return !uri.isOpaque();
	}
	
	/**
	 * Validates the local location of resources.
	 * @param uri the location of the resource.
	 * @return {@code true} if the specified location is valid.
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