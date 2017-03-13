package com.github.i49.pulp.core.zip;

import static com.github.i49.pulp.core.Messages.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;

/**
 * ZIP file parser.
 */
class ZipParser implements AutoCloseable {

	/**
	 * Each entry of the Central Directory in ZIP file.
	 */
	static class CentralDirectoryEntry {
		
		private long position;
		private String fileName;
		
		public CentralDirectoryEntry(String fileName, long position) {
			this.fileName = fileName;
			this.position = position;
		}
		
		public long getPosition() {
			return position;
		}
		
		public String getFileName() {
			return fileName;
		}
	}

	/**
	 * End of Central Directory in ZIP file.
	 */
	private static class CentralDirectoryEnd {
		private long centralDirectoryPosition;
		private long centralDirectoryLength;
	}
	
	private final Charset charset;
	private final RandomAccessFile file;
	
	/**
	 * Constructs this parser.
	 * @param path the path of the ZIP file to parse.
	 * @throws FileNotFoundException if the ZIP file was not found.
	 */
	public ZipParser(Path path) throws FileNotFoundException {
		this(path, StandardCharsets.UTF_8);
	}

	/**
	 * Constructs this parser.
	 * @param path the path of the ZIP file to parse.
	 * @param charset the encoding to be used to decode the entry names in the ZIP file. 
	 * @throws FileNotFoundException if the ZIP file was not found.
	 */
	public ZipParser(Path path, Charset charset) throws FileNotFoundException {
		this.charset = (charset == null) ? StandardCharsets.UTF_8 : charset;
		this.file = new RandomAccessFile(path.toString(), "r");
	}
	
	@Override
	public void close() throws IOException {
		file.close();
	}

	public Map<String, CentralDirectoryEntry> parse() throws IOException {
		CentralDirectoryEnd end = findCentralDirectoryEnd();
		if (end == null) {
			throw new ZipException(END_OF_CENTRAL_DIRECTORY_NOT_FOUND());
		}
		return parseCentralDirectory(end.centralDirectoryPosition, end.centralDirectoryLength);
	}
	
	private CentralDirectoryEnd findCentralDirectoryEnd() throws IOException {

		final long fileLength = file.length();
		int bufferSize = 64 * 1024 + 22;
		if ((long)bufferSize > fileLength) {
			bufferSize = (int)fileLength;
		}

		byte[] buffer = new byte[bufferSize];
		file.seek(fileLength - bufferSize);
		file.readFully(buffer); 
		
		final byte[] signature = {0x50, 0x4B, 0x05, 0x06}; 
		int step = 3;
		for (int pos = bufferSize - 19; pos >= 0; pos--) {
			int b = buffer[pos];
			if (b == signature[step]) {
				if (--step < 0) {
					ZipStructure s = ZipStructure.wrap(buffer, pos);
					if (22 + s.getUint16(20) == s.length()) {
						return parseEndOfCentralDirectory(s);
					} else {
						step = 3;
					}
				}
			} else {
				step = 3;
			}
		}
		return null;
	}
	
	private CentralDirectoryEnd parseEndOfCentralDirectory(ZipStructure s) {
		CentralDirectoryEnd end = new CentralDirectoryEnd();
		end.centralDirectoryLength = s.getUint32(12);
		end.centralDirectoryPosition = s.getUint32(16);
		return end;
	}
	
	private Map<String, CentralDirectoryEntry> parseCentralDirectory(long offset, long length) throws IOException {
		Map<String, CentralDirectoryEntry> entries = new HashMap<>();
		file.seek(offset);
		int bufferSize = (int)length;
		byte[] buffer = new byte[bufferSize];
		file.readFully(buffer);
		final byte[] signature = {0x50, 0x4B, 0x01, 0x02}; 
		int pos = 0;
		while (pos < bufferSize) {
			if (buffer[pos] != signature[0] ||
			    buffer[pos + 1] != signature[1] ||
			    buffer[pos + 2] != signature[2] ||
			    buffer[pos + 3] != signature[3]) {
				throw new ZipException(CENTRAL_DIRECTORY_ENTRY_BROKEN());
			}
			pos += parseCentralDirectoryEntry(ZipStructure.wrap(buffer, pos), entries);
		}
		return entries;
	}
	
	private int parseCentralDirectoryEntry(ZipStructure s, Map<String, CentralDirectoryEntry> entries) {
		int n = s.getUint16(28);
		int m = s.getUint16(30);
		int k = s.getUint16(32);
		long position = s.getUint32(42);
		String fileName = s.getString(46, n, this.charset); 
		CentralDirectoryEntry entry = new CentralDirectoryEntry(fileName, position);
		entries.put(entry.getFileName(), entry);
		return 46 + n + m + k;
	}
}
