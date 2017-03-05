package com.github.i49.pulp.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.i49.pulp.api.MediaType;

class ForeignMediaType implements MediaType {
	
	private static final Map<String, MediaType> mediaTypes = Collections.synchronizedMap(new HashMap<>());

	private final String type;
	private final String subtype;
	
	private ForeignMediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
	}
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getSubtype() {
		return subtype;
	}
	
	@Override
	public String toString() {
		return getType() + "/" + getSubtype();
	}
	
	/**
	 * Creates a new instance of {@link MediaType} by parsing the supplied string.
	 * @param value the media type string.
	 * @return the newly created {@link MediaType}.
	 * @exception IllegalArgumentException if {@code value} cannot be parsed or is {@code null}.
	 */
	public static MediaType valueOf(String value) {
		if (value == null) {
			throw new IllegalArgumentException(Messages.NULL_PARAMETER("value"));
		}
		value = value.trim();
		MediaType mediaType = mediaTypes.get(value);
		if (mediaType == null) {
			String[] parts = value.split("/"); 
			if (parts.length == 2) {
				mediaType = new ForeignMediaType(parts[0], parts[1]); 
				mediaTypes.put(value, mediaType);
			} else {
				throw new IllegalArgumentException(Messages.MEDIA_TYPE_INVALID(value));
			}
		}
		return mediaType;
	}
}
