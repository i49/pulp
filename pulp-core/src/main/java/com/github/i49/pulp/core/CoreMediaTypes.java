package com.github.i49.pulp.core;

import java.net.URI;

import com.github.i49.pulp.api.CoreMediaType;

final class CoreMediaTypes {

	/**
	 * Guesses media type from a file extension.
	 * @param location the location of the resource.
	 * @return the media type found or {@code null} if not found.
	 */
	public static CoreMediaType guessMediaType(URI location) {
		if (location == null) {
			return null;
		}
		String path = location.getPath();
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}
		String extension = path.substring(lastIndex + 1);
		CoreMediaType mediaType = null;
		switch (extension.toLowerCase()) {
		case "xhtml":
			mediaType = CoreMediaType.APPLICATION_XHTML_XML;
			break;
		case "ncx":
			mediaType = CoreMediaType.APPLICATION_NCX_XML;
			break;
		case "otf":
		case "ttf":
			mediaType = CoreMediaType.APPLICATION_OPENTYPE;
			break;
		case "gif":
			mediaType = CoreMediaType.IMAGE_GIF;
			break;
		case "jpg":
		case "jpeg":
			mediaType = CoreMediaType.IMAGE_JPEG;
			break;
		case "png":
			mediaType = CoreMediaType.IMAGE_PNG;
			break;
		case "svg":
			mediaType = CoreMediaType.IMAGE_SVG_XML;
			break;
		case "css":
			mediaType = CoreMediaType.TEXT_CSS;
			break;
		case "js":
			mediaType = CoreMediaType.TEXT_JAVASCRIPT;
			break;
		}
		return mediaType;
	}
	
	private CoreMediaTypes() {
	}
}
