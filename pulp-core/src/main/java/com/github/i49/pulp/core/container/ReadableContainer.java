package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public abstract class ReadableContainer extends AbstractContainer {

	protected ReadableContainer(Path path) {
		super(path);
	}

	public abstract byte[] readItem(String location) throws IOException;
	
	public abstract InputStream openItemToRead(String location) throws IOException;
}
