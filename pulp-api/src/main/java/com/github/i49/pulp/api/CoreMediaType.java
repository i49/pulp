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

package com.github.i49.pulp.api;

/**
 * A media type for {@link PublicationResource} that EPUB reading systems have to support.
 */
public enum CoreMediaType implements MediaType {

	/** GIF images */
	IMAGE_GIF("image", "gif"),
	/** JPEG images */
	IMAGE_JPEG("image", "jpeg"),
	/** PNG images */
	IMAGE_PNG("image", "png"),
	/** SVG documents */
	IMAGE_SVG_XML("image", "svg+xml"),

	/** XHTML documents that use the XHTML syntax */
	APPLICATION_XHTML_XML("application", "xhtml+xml"),
	/** JavaScript */
	APPLICATION_JAVASCRIPT("application", "javascript"),
	/** Navigation Center eXtended */
	APPLICATION_DTBNCX_XML("application", "x-dtbncx+xml"),
	/** OpenType and TrueType fonts */
	APPLICATION_OPENTYPE("application", "application/font-sfnt"),
	/** WOFF fonts */
	APPLICATION_FONT_WOFF("application", "font-woff"),
	/** EPUB media overlay documents */
	APPLICATION_SMIL_XML("application", "smil+xml"),
	/** Text-to-Speech (TTS) pronunciation lexicons */ 
	APPLICAIION_PLS_XML("application", "pls+xml"),
	
	/** MP3 audio */
	AUDIO_MPEG("audio", "mpeg"),
	/** AAC LC audio using MP4 container */
	AUDIO_MP4("audio", "mp4"),
	
	/** CSS style sheets */
	TEXT_CSS("text", "css"),
	/** WOFF2 fonts */
	FONT_WOFF2("font", "woff2"), 
	;
	
	private final String type;
	private final String subtype;
	
	/**
	 * Constructs a core media type.
	 * @param type the type part of the media type.
	 * @param subtype the subtype part of the media type.
	 */
	private CoreMediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
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
