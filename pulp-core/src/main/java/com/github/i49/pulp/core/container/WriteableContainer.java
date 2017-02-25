package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public abstract class WriteableContainer extends AbstractContainer {
	
	protected WriteableContainer(Path path) {
		super(path);
	}

	public abstract void writeItem(String location, byte[] content) throws IOException;
	
	public abstract OutputStream openItemToWrite(String location) throws IOException;
}
