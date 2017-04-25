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

import static com.github.i49.pulp.api.core.CoreMediaType.*;

import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.core.CoreMediaType;
import com.github.i49.pulp.api.core.MediaType;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The central registry of media types.
 *
 * <p>Instances of this class are safe for use by multiple concurrent threads. </p>
 */
public class MediaTypeRegistry {

	private final Map<String, MediaType> mediaTypes = new HashMap<>();
	
	@SuppressWarnings("serial")
	private final Map<String, MediaType> extensionMap = new HashMap<String, MediaType>() {{
		put("xhtml", APPLICATION_XHTML_XML);
		put("ncx", APPLICATION_DTBNCX_XML);
		put("otf", APPLICATION_OPENTYPE);
		put("ttf", APPLICATION_OPENTYPE);
		put("gif", IMAGE_GIF);
		put("jpg", IMAGE_JPEG);
		put("jpeg", IMAGE_JPEG);
		put("png", IMAGE_PNG);
		put("svg", IMAGE_SVG_XML);
		put("css", TEXT_CSS);
		put("js", APPLICATION_JAVASCRIPT);
	}};
	
	/**
	 * Constructs this registry.
	 */
	public MediaTypeRegistry() {
		// registers all core media types.
		for (MediaType type: CoreMediaType.values()) {
			mediaTypes.put(type.toString(), type);
		}
		for (MediaType type: StandardMediaType.values()) {
			mediaTypes.put(type.toString(), type);
		}
	}
	
	/**
	 * Returns an instance of {@link MediaType} by parsing the supplied string.
	 * 
	 * @param value the media type string.
	 * @return the instance of {@link MediaType}.
	 * @throws IllegalArgumentException if {@code value} cannot be parsed or is {@code null}.
	 */
	public synchronized MediaType getMediaType(String value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null");
		}
		value = value.trim();
		MediaType mediaType = mediaTypes.get(value);
		if (mediaType == null) {
			mediaType = addMediaType(value);
		}
		return mediaType;
	}
	
	/**
	 * Guesses media type from a file extension.
	 * 
	 * @param name the pathname of the resource.
	 * @return the media type found or {@code null} otherwise.
	 */
	public MediaType guessMediaType(String name) {
		if (name == null) {
			throw new IllegalArgumentException("\"name\" must not be null");
		}
		int lastIndex = name.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}
		String extension = name.substring(lastIndex + 1).toLowerCase();
		return extensionMap.get(extension);
	}
	
	public boolean checkIfTypeIsXml(MediaType mediaType) {
		String subtype = mediaType.getSubtype();
		return subtype.equals("xml") || subtype.endsWith("+xml");
	}
	
	private MediaType addMediaType(String value) {
		String[] parts = value.split("/"); 
		if (parts.length != 2) {
			throw new IllegalArgumentException(Messages.MEDIA_TYPE_INVALID(value));
		}
		MediaType mediaType = new GenericMediaType(parts[0], parts[1]); 
		mediaTypes.put(mediaType.toString(), mediaType);
		return mediaType;
	}

	/**
	 * Generic implementation of {@link MediaType}.
	 */
	private static class GenericMediaType implements MediaType {

		private final String type;
		private final String subtype;
		
		public GenericMediaType(String type, String subtype) {
			this.type = type.trim();
			this.subtype = subtype.trim();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getType() {
			return type;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getSubtype() {
			return subtype;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return getType() + "/" + getSubtype();
		}
	}
}
