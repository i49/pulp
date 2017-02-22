package com.github.i49.pulp.core.container;

import java.io.Closeable;
import java.io.OutputStream;

public interface ContainerSaver extends Closeable {
	
	void save(String location, byte[] content) throws Exception;
	
	void save(String location, ThrowingConsumer<OutputStream> consumer) throws Exception;
}
