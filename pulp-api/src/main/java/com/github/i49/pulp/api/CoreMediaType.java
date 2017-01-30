package com.github.i49.pulp.api;

/**
 * The media types which specify types and formats of the Publication Resources
 * contained in a publication.
 */
public enum CoreMediaType {

	IMAGE_GIF("image", "gif"),
	IMAGE_JPEG("image", "jpeg"),
	IMAGE_PNG("image", "png"),
	IMAGE_SVG_XML("image", "svg+xml"),

	APPLICATION_XHTML_XML("application", "xhtml+xml"),
	APPLICATION_NCX_XML("application", "x-dtbncx+xml"),
	APPLICATION_OPENTYPE("application", "vnd.ms-opentype"),
	APPLICATION_WOFF_FONT("application", "font-woff"),
	APPLICATION_SMIL_XML("application", "smil+xml"),
	APPLICAIION_PLS_XML("application", "pls+xml"),
	
	AUDIO_MPEG("audio", "mpeg"),
	AUDIO_MP4("audio", "mp4"),
	
	TEXT_CSS("text", "css"),
	TEXT_JAVASCRIPT("text", "javascript")
	;
	
	private final String type;
	private final String subtype;
	
	/**
	 * Constructs a media type.
	 * @param type the type part of the media type.
	 * @param subtype the subtype part of the media type.
	 */
	private CoreMediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
	}
	
	/**
	 * Returns the type part of this media type.
	 * @return the type part of this media type. 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns the subtype part of this media type.
	 * @return the subtype part of this media type. 
	 */
	public String getSubtype() {
		return subtype;
	}

	/**
	 * Return the string representation of this media type.
	 * @return the string representation of this media type.
	 */
	@Override
	public String toString() {
		return type + "/" + subtype;
	}
}
