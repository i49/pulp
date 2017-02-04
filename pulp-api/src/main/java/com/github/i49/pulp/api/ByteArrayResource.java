package com.github.i49.pulp.api;

/**
 * A publication resource that provides its contents as a byte array.
 */
public interface ByteArrayResource extends PublicationResource {

	/**
	 * Returns the contents of this resource as a byte array.
	 * @return a byte array.
	 */
	byte[] getByteArray();
}
