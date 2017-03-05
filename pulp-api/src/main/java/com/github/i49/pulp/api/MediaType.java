package com.github.i49.pulp.api;

/**
 * The MIME media type used to specify the type and the format of {@link PublicationResource}.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2046">RFC 2046</a>
 */
public interface MediaType {

	/**
	 * Returns the type part of this media type.
	 * @return the type part of this media type. 
	 */
	String getType();
	
	/**
	 * Returns the subtype part of this media type.
	 * @return the subtype part of this media type. 
	 */
	String getSubtype();
	
	/**
	 * Returns the string representation of this media type.
	 * @return the string representation of this media type.
	 */
	String toString();
}
