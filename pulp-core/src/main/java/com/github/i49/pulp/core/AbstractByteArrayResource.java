package com.github.i49.pulp.core;

import java.io.IOException;
import java.io.OutputStream;

import com.github.i49.pulp.api.ByteArrayResource;
import com.github.i49.pulp.api.CoreMediaType;

/**
 * A skeletal class implementing {@link ByteArrayResource}.
 */
abstract class AbstractByteArrayResource extends AbstractPublicationResource implements ByteArrayResource {

	protected AbstractByteArrayResource(String name, CoreMediaType mediaType) {
		super(name, mediaType);
	}

	@Override
	public void persist(OutputStream stream) throws IOException {
		stream.write(getByteArray());
	}
}
