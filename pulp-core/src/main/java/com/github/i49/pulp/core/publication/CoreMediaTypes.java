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

import java.util.EnumSet;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.MediaType;

/**
 * A supporting class for {@link CoreMediaType}.
 */
final class CoreMediaTypes {
	
	private static final EnumSet<CoreMediaType> XML_TYPES = EnumSet.of(
			CoreMediaType.APPLICAIION_PLS_XML,
			CoreMediaType.APPLICATION_DTBNCX_XML,
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
			mediaType = CoreMediaType.APPLICATION_DTBNCX_XML;
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
			mediaType = CoreMediaType.APPLICATION_JAVASCRIPT;
			break;
		}
		return mediaType;
	}
	
	public static boolean checkTypeIsXml(MediaType mediaType) {
		return XML_TYPES.contains(mediaType);
	}
	
	private CoreMediaTypes() {
	}
}
