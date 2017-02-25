package com.github.i49.pulp.core.container;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WriteableZipContainer extends WriteableContainer {

	private final ZipOutputStream zstream;
	
	public WriteableZipContainer(Path path) throws IOException {
		super(path);
		this.zstream = new ZipOutputStream(Files.newOutputStream(path));
	}

	public WriteableZipContainer(OutputStream stream) {
		super(null);
		this.zstream = new ZipOutputStream(stream);
	}

	@Override
	public void writeItem(String pathname, byte[] content) throws IOException {
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
	public OutputStream openItemToWrite(String pathname) throws IOException {
		ZipEntry entry = new ZipEntry(pathname);
		entry.setMethod(ZipEntry.DEFLATED);
		this.zstream.putNextEntry(entry);
		return new EntryOutputStream(this.zstream);
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
	
	private static class EntryOutputStream extends FilterOutputStream {

		public EntryOutputStream(OutputStream out) {
			super(out);
		}

		@Override
		public void close() throws IOException {
			ZipOutputStream stream = (ZipOutputStream)out;
			stream.closeEntry();
			stream.flush();
		}
	}
}
