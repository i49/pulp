package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipContainerSaver implements ContainerSaver {

	private final Path path;
	private final ZipOutputStream zstream;
	
	public ZipContainerSaver(Path path) throws IOException {
		this.path = path;
		this.zstream = new ZipOutputStream(Files.newOutputStream(path));
	}

	public ZipContainerSaver(OutputStream stream) {
		this.path = null;
		this.zstream = new ZipOutputStream(stream);
	}

	@Override
	public void save(String pathname, byte[] content) throws IOException {
		ZipEntry entry = new ZipEntry(pathname);
		entry.setCrc(computeCRC(content));
		entry.setSize(content.length);
		entry.setMethod(ZipEntry.STORED);
		this.zstream.putNextEntry(entry);
		this.zstream.write(content);
		this.zstream.closeEntry();
		this.zstream.flush();
	}

	@Override
	public void save(String pathname, ThrowingConsumer<OutputStream> consumer) throws Exception {
		ZipEntry entry = new ZipEntry(pathname);
		entry.setMethod(ZipEntry.DEFLATED);
		this.zstream.putNextEntry(entry);
		consumer.accept(this.zstream);
		this.zstream.closeEntry();
		this.zstream.flush();
	}

	@Override
	public void close() throws IOException {
		this.zstream.close();
	}
	
	private static long computeCRC(byte[] content) {
		CRC32 crc = new CRC32();
		crc.update(content);
		return crc.getValue();
	}
}
