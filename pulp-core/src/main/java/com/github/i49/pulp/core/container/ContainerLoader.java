package com.github.i49.pulp.core.container;

import java.io.Closeable;
import java.io.InputStream;

public interface ContainerLoader extends Closeable {

	byte[] load(String location);
	
	void load(String location, ThrowingConsumer<InputStream> consumer);
}
