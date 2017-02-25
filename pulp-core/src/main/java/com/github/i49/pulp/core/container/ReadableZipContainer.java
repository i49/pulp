package com.github.i49.pulp.core.container;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ReadableZipContainer extends ReadableContainer {
	
	private static final int BUFFER_SIZE = 128 * 1024;
	
	private final ZipFile zip;
	
	public ReadableZipContainer(Path path) throws IOException {
		super(path);
		this.zip = new ZipFile(path.toFile(), StandardCharsets.UTF_8);
	}

	@Override
	public byte[] readItem(String location) throws IOException {
		try (InputStream in = openItemToRead(location)) {
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
		ZipEntry entry = findEntry(location);
		if (entry == null) {
			throw new FileNotFoundException(location);
		}
		return this.zip.getInputStream(entry);
	}
	
	@Override
	public void close() throws IOException {
		this.zip.close();
	}
	
	private ZipEntry findEntry(String location) {
		return this.zip.getEntry(location);
	}
}
