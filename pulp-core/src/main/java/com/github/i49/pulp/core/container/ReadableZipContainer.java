package com.github.i49.pulp.core.container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import com.github.i49.pulp.api.ContentSource;
import com.github.i49.pulp.core.zip.ZipLoader;

public class ReadableZipContainer extends ReadableContainer {
	
	private static final int BUFFER_SIZE = 128 * 1024;
	
	private final ZipLoader loader;
	private final ZipContentSource contentSource;
	
	public ReadableZipContainer(Path path) throws IOException {
		super(path);
		this.loader = ZipLoader.create(path, StandardCharsets.UTF_8);
		this.contentSource = new ZipContentSource();
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
	public ContentSource getContentSource() {
		return contentSource;
	}
	
	@Override
	public void close() {
		// Nothing to do.
	}
	
	/**
	 * Implementation of {@link ContentSource} that will load resource content from this ZIP file.
	 */
	private class ZipContentSource implements ContentSource {
		
		@Override
		public InputStream openSource(URI location) throws IOException {
			String entryName = location.getPath();
			return loader.openToLoad(entryName);
		}
	}
}
