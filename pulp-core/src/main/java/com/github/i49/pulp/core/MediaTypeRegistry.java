package com.github.i49.pulp.core;

import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.MediaType;

/**
 * The central registry of media types.
 *
 * <p>Instances of this class are safe for use by multiple concurrent threads. </p>
 */
public class MediaTypeRegistry {

	private final Map<String, MediaType> mediaTypes = new HashMap<>();
	
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
	 * @param value the media type string.
	 * @return the instance of {@link MediaType}.
	 * @exception IllegalArgumentException if {@code value} cannot be parsed or is {@code null}.
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
