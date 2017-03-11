package com.github.i49.pulp.core.container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import com.github.i49.pulp.core.zip.ZipLoader;

public class ReadableZipContainer extends ReadableContainer {
	
	private static final int BUFFER_SIZE = 128 * 1024;
	
	private final ZipLoader loader;
	
	public ReadableZipContainer(Path path) throws IOException {
		super(path);
		this.loader = ZipLoader.create(path, StandardCharsets.UTF_8);
	}

	@Override
	public byte[] readItem(String location) throws IOException {
		try (InputStream in = loader.openToLoad(location)) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			return out.toByteArray();
		}
	}

	@Override
	public InputStream openItemToRead(String location) throws IOException {
		return loader.openToLoad(location);
	}
	
	@Override
	public void close() throws IOException {
	}
}
