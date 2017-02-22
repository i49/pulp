package com.github.i49.pulp.core.container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipContainerLoader implements ContainerLoader {
	
	private static final int BUFFER_SIZE = 128 * 1024;
	
	private final Path path;
	private final ZipFile zip;
	
	public ZipContainerLoader(Path path) throws IOException {
		this.path = path;
		this.zip = new ZipFile(path.toFile(), StandardCharsets.UTF_8);
	}

	@Override
	public byte[] load(String location) {
		ZipEntry entry = findEntry(location);
		if (entry == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (InputStream in = this.zip.getInputStream(entry)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			return out.toByteArray();
		} catch (IOException e) {
			// TODO:
			return null;
		}
	}

	@Override
	public void load(String location, ThrowingConsumer<InputStream> consumer) {
		ZipEntry entry = findEntry(location);
		if (entry == null) {
			return;
		}
		try (InputStream in = this.zip.getInputStream(entry)) {
			consumer.accept(in);
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() throws IOException {
		this.zip.close();
	}
	
	private ZipEntry findEntry(String location) {
		return this.zip.getEntry(location);
	}
}
