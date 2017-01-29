package com.github.i49.pulp.api;

import java.io.IOException;
import java.io.InputStream;

public interface AuxiliaryResource extends PublicationResource {

	InputStream openOctetStream() throws IOException;
}
