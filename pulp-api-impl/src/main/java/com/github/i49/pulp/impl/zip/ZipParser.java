/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.i49.pulp.impl.zip;

import static com.github.i49.pulp.impl.base.Messages.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

/**
 * ZIP file parser.
 */
class ZipParser implements AutoCloseable {

	/**
	 * End of Central Directory in ZIP file.
	 */
	private static class CentralDirectoryEnd {
		private long centralDirectoryPosition;
		private long centralDirectoryLength;
	}
	
	private final Charset charset;
	private final RandomAccessFile file;
	
	private static final int MIN_SIZE_OF_CENTRAL_DIRECTORY_END = 22;
	
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

	/**
	 * Parses the central directory of a ZIP file.
	 * @return the list of entries in the central directory. 
	 * @throws ZipException if the parsed file was not a valid ZIP file.
	 * @throws IOException if I/O error has occurred while reading the content of the file.
	 */
	public List<CentralDirectoryEntry> parse() throws IOException {
		CentralDirectoryEnd end = findCentralDirectoryEnd();
		if (end == null) {
			throw new ZipException(END_OF_CENTRAL_DIRECTORY_NOT_FOUND());
		}
		return parseCentralDirectory(end.centralDirectoryPosition, end.centralDirectoryLength);
	}
	
	private CentralDirectoryEnd findCentralDirectoryEnd() throws IOException {

		final long fileLength = file.length();
		if (fileLength < MIN_SIZE_OF_CENTRAL_DIRECTORY_END) {
			return null;
		}
		int bufferSize = 64 * 1024 + MIN_SIZE_OF_CENTRAL_DIRECTORY_END;
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
	
	private List<CentralDirectoryEntry> parseCentralDirectory(long offset, long length) throws IOException {
		List<CentralDirectoryEntry> entries = new ArrayList<>();
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
	
	private int parseCentralDirectoryEntry(ZipStructure s, List<CentralDirectoryEntry> entries) {
		long compressedSize = s.getUint32(20);
		long uncompressedSize = s.getUint32(24);
		int n = s.getUint16(28);
		int m = s.getUint16(30);
		int k = s.getUint16(32);
		long position = s.getUint32(42);
		String fileName = s.getString(46, n, this.charset); 
		CentralDirectoryEntry entry = new CentralDirectoryEntry(fileName, position, compressedSize, uncompressedSize);
		entries.add(entry);
		return 46 + n + m + k;
	}
}
