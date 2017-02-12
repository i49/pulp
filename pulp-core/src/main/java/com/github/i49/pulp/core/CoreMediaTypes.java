package com.github.i49.pulp.core;

import java.util.EnumSet;

import com.github.i49.pulp.api.CoreMediaType;

final class CoreMediaTypes {

	private static final EnumSet<CoreMediaType> xmlSet = EnumSet.of(
			CoreMediaType.APPLICAIION_PLS_XML,
			CoreMediaType.APPLICATION_NCX_XML,
			CoreMediaType.APPLICATION_SMIL_XML,
			CoreMediaType.APPLICATION_XHTML_XML,
			CoreMediaType.IMAGE_SVG_XML);
	
	/**
	 * Guesses media type from a file extension.
	 * @param location the location of the resource.
	 * @return the media type found or {@code null} if not found.
	 */
	public static CoreMediaType guessMediaType(String name) {
		if (name == null) {
			return null;
		}
		int lastIndex = name.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}
		String fileExtension = name.substring(lastIndex + 1);
		CoreMediaType mediaType = null;
		switch (fileExtension.toLowerCase()) {
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
	
	public static boolean checkTypeIsXml(CoreMediaType mediaType) {
		return xmlSet.contains(mediaType);
	}
	
	private CoreMediaTypes() {
	}
}
