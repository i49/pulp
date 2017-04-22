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

/**
 * An entry of the Central Directory in a ZIP file.
 */
class CentralDirectoryEntry {
	
	private final String fileName;
	private final long position;
	private final long compressedSize;
	private final long uncompressedSize;
	
	/**
	 * Constructs this entry.
	 * 
	 * @param fileName the file name of the entry.
	 * @param position the file offset of the entry from the beginning of the file.
	 * @param compressedSize the size of the entry in bytes after compression.
	 * @param uncompressedSize the size of the entry in bytes before compression.
	 */
	public CentralDirectoryEntry(String fileName, long position, long compressedSize, long uncompressedSize) {
		this.fileName = fileName;
		this.position = position;
		this.compressedSize = compressedSize;
		this.uncompressedSize = uncompressedSize;
	}
	
	/**
	 * Returns the file name of the entry.
	 * 
	 * @return the file name in the ZIP file.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Return the offset of the entry.
	 * 
	 * @return the offset from the beginning of the file.
	 */
	public long getPosition() {
		return position;
	}
	
	/**
	 * Return the size of the entry in bytes when compressed.
	 * 
	 * @return the size of the entry in bytes.
	 */
	public long getCompressedSize() {
		return compressedSize;
	}
	
	/**
	 * Return the size of the entry in bytes when expanded.
	 * 
	 * @return the size of the entry in bytes.
	 */
	public long getUncompressedSize() {
		return uncompressedSize;
	}
}