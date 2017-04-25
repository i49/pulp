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
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

/**
 * ZIP entry loader.
 */
public class ZipLoader {

	private final Path path;
	private final Map<String, CentralDirectoryEntry> entryMap;
	private final List<CentralDirectoryEntry> orderedEntries;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param path the path to the ZIP file.
	 * @param charset the character set to be used to decode the ZIP entry name.
	 * @return newly created instance of this class.
	 * @throws IllegalArgumentException if {@code path} is {@code null}.
	 * @throws IOException if an I/O error has occurred.
	 * @throws ZipException if a ZIP format error has occurred.
	 */
	public static ZipLoader create(Path path, Charset charset) throws IOException {
		if (path == null) {
			throw new IllegalArgumentException("path is null.");
		}
		List<CentralDirectoryEntry> entries = null;
		try (ZipParser parser = new ZipParser(path, charset)) {
			entries = parser.parse();
		}
		return new ZipLoader(path, entries);
	}
	
	/**
	 * Constructs this loader.
	 * 
	 * @param path the path to the ZIP file.
	 * @param entries the list of entries in Central Directory of the ZIP file.
	 */
	private ZipLoader(Path path, List<CentralDirectoryEntry> entries) {
		this.path = path;
		this.entryMap = new HashMap<>();
		for (CentralDirectoryEntry entry: entries) {
			this.entryMap.put(entry.getFileName(), entry);
		}
		this.orderedEntries = new ArrayList<>(entries);
		Collections.sort(this.orderedEntries, (e1, e2)->{
			if (e1.getPosition() < e2.getPosition()) {
				return -1;
			} else if (e1.getPosition() > e2.getPosition()) {
				return 1;
			}
			return 0;
		});
	}
	
	/**
	 * Returns the path of the ZIP file.
	 * 
	 * @return the path of the ZIP file.
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * Returns the total number of entries in the ZIP file.
	 * 
	 * @return the total number of entries in the ZIP file.
	 */
	public int getNumberOfEntries() {
		return this.entryMap.size();
	}
	
	/**
	 * Returns whether the entry specified by name exists in the ZIP file or not.
	 *  
	 * @param entryName the name of the entry.
	 * @return {@code true} if the entry exists, {@code false} otherwise.
	 */
	public boolean findEntry(String entryName) {
		return getNullableEntry(entryName) != null;
	}

	/**
	 * Returns the name of the ZIP entry located at specified index.
	 * 
	 * @param index the index of the entry.
	 * @return the name of the entry.
	 */
	public String getEntryName(int index) {
		return getEntryAt(index).getFileName();
	}

	/**
	 * Loads the content of the ZIP entry.
	 * 
	 * @param entryName the name of the entry.
	 * @return loaded bytes.
	 * @throws IllegalArgumentException if {@code entryName} is {@code null}.
	 * @throws FileNotFoundException if the specified entry was not found in the ZIP file.
	 * @throws IOException if an I/O error has occurred.
	 */
	public byte[] load(String entryName) throws IOException {
		if (entryName == null) {
			throw new IllegalArgumentException("entryName is null.");
		}
		CentralDirectoryEntry entry = getEntry(entryName);
		final int contentSize = (int)entry.getUncompressedSize();
		byte[] content = new byte[contentSize];
		try (InputStream stream = openToLoad(entryName)) {
			int offset = 0;
			while (offset < content.length) {
				int bytesRead = stream.read(content, offset, content.length - offset);
				if (bytesRead < 0) {
					break;
				}
				offset += bytesRead;
			}
		}
		return content;
	}
	
	/**
	 * Opens a new {@link InputStream} to load an entry of ZIP file.
	 * 
	 * @param entryName the filename of the entry to load.
	 * @return newly created {@link InputStream} that must be closed by the caller.
	 * @throws IllegalArgumentException if {@code entryName} is {@code null}.
	 * @throws FileNotFoundException if the specified entry was not found in the ZIP file.
	 * @throws IOException if an I/O error has occurred.
	 */
	public InputStream openToLoad(String entryName) throws IOException {
		if (entryName == null) {
			throw new IllegalArgumentException("entryName is null.");
		}
		CentralDirectoryEntry entry = getEntry(entryName);
		InputStream stream = Files.newInputStream(this.path);
		stream.skip(entry.getPosition());
		ZipInputStream zstream = new ZipInputStream(stream);
		zstream.getNextEntry();
		return zstream;
	}
	
	private CentralDirectoryEntry getEntry(String entryName) throws FileNotFoundException {
		CentralDirectoryEntry entry = getNullableEntry(entryName);
		if (entry == null) {
			throw new FileNotFoundException(ZIP_ENTRY_NOT_FOUND(entryName, getPath()));
		}
		return entry;
	}
	
	private CentralDirectoryEntry getNullableEntry(String entryName) {
		return this.entryMap.get(entryName);
	}

	private CentralDirectoryEntry getEntryAt(int index) {
		if (index < 0 || index >= getNumberOfEntries()) {
			throw new IndexOutOfBoundsException();
		}
		return this.orderedEntries.get(index);
	}
}
