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
	
	public static PublicationResourceLocation create(String location) {
		try {
			return create(new URI(location));
		} catch (URISyntaxException e) {
			throw new EpubException(Messages.RESOURCE_LOCATION_INVALID(location));
		}
	}
	
	public static PublicationResourceLocation create(URI location) {
		if (!validateLocation(location)) {
			throw new EpubException(Messages.RESOURCE_LOCATION_INVALID(location.toString()));
		}
		return new PublicationResourceLocation(location);
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
