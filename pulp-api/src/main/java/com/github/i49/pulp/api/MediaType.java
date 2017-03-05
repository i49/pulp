package com.github.i49.pulp.api;

/**
 * The media type that specifies the type and the format of a resource.
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
