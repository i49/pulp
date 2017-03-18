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

package com.github.i49.pulp.core.zip;

import static com.github.i49.pulp.core.Messages.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import com.github.i49.pulp.core.zip.ZipParser.CentralDirectoryEntry;

/**
 * ZIP entry loader.
 */
public class ZipLoader {

	private final Path path;
	private Map<String, CentralDirectoryEntry> entries;
	
	/**
	 * Creates a new instance of this class.
	 * @param path the path to the ZIP file.
	 * @param charset the charset to be used to decode the ZIP entry name.
	 * @return newly created instance of this class.
	 * @exception IllegalArgumentException if {@code path} is {@code null}.
	 * @exception IOException if an I/O error has occurred.
	 * @exception ZipException if a ZIP format error has occurred.
	 */
	public static ZipLoader create(Path path, Charset charset) throws IOException {
		if (path == null) {
			throw new IllegalArgumentException("path is null.");
		}
		Map<String, CentralDirectoryEntry> entries = null;
		try (ZipParser parser = new ZipParser(path, charset)) {
			entries = parser.parse();
		}
		return new ZipLoader(path, entries);
	}
	
	private ZipLoader(Path path, Map<String, CentralDirectoryEntry> entries) {
		this.path = path;
		this.entries = entries;
	}
	
	/**
	 * Returns the path of the ZIP file.
	 * @return the path of the ZIP file.
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * Opens a new {@link InputStream} to load an entry of ZIP file.
	 * @param entryName the filename of the entry to load.
	 * @return newly created {@link InputStream}.
	 * @exception IllegalArgumentException if {@code entryName} is {@code null}.
	 * @exception FileNotFoundException if the specified entry was not found in the ZIP file.
	 * @exception IOException if an I/O error has occurred.
	 */
	public InputStream openToLoad(String entryName) throws IOException {
		if (entryName == null) {
			throw new IllegalArgumentException("entryName is null.");
		}
		CentralDirectoryEntry entry = findEntry(entryName);
		if (entry == null) {
			throw new FileNotFoundException(ZIP_ENTRY_NOT_FOUND(entryName, getPath()));
		}
		InputStream stream = Files.newInputStream(this.path);
		stream.skip(entry.getPosition());
		ZipInputStream zstream = new ZipInputStream(stream);
		zstream.getNextEntry();
		return zstream;
	}
	
	private CentralDirectoryEntry findEntry(String entryName) {
		return this.entries.get(entryName);
	}
}
