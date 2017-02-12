package com.github.i49.pulp.api;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.function.Supplier;

public interface PublicationResourceBuilder {

	PublicationResourceBuilder content(Path path);

	PublicationResourceBuilder content(URI uri);
	
	PublicationResourceBuilder content(byte[] bytes);
	
	PublicationResourceBuilder content(Supplier<InputStream> supplier);
	
	PublicationResource build();
}
