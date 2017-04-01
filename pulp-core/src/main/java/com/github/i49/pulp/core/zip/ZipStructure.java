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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A structure in ZIP format.
 */
class ZipStructure {
	
	private final byte[] buffer;
	private final int offset;
	private final int length;

	/**
	 * Creates a structure by wrapping the whole buffer.
	 * @param buffer the buffer backing the structure.
	 * @return created structure.
	 */
	public static ZipStructure wrap(byte[] buffer) {
		return new ZipStructure(buffer, 0, buffer.length);
	}

	/**
	 * Creates a structure by wrapping the part of the buffer.
	 * The structure ends at the end of the buffer.
	 * @param buffer the buffer backing the structure.
	 * @param offset the position of the structure in the buffer.
	 * @return created structure.
	 */
	public static ZipStructure wrap(byte[] buffer, int offset) {
		int length = buffer.length - offset;
		return new ZipStructure(buffer, offset, length);
	}

	/**
	 * Creates a structure by wrapping the part of the buffer.
	 * @param buffer the buffer backing the structure.
	 * @param offset the position of the structure in the buffer.
	 * @param length the length of the structure.
	 * @return created structure.
	 */
	public static ZipStructure wrap(byte[] buffer, int offset, int length) {
		return new ZipStructure(buffer, offset, length);
	}
	
	/**
	 * Constructs this structure.
	 * @param buffer the buffer backing the structure.
	 * @param offset the position of the structure in the buffer.
	 * @param length the length of the structure.
	 */
	private ZipStructure(byte[] buffer, int offset, int length) {
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
	}
	
	/**
	 * Returns the length of this structure.
	 * @return the length of this structure.
	 */
	public int length() {
		return length;
	}
	
	/**
	 * Retrieves an unsigned 8bit integer from this structure.
	 * @param offset the relative position to the start of the structure.
	 * @return retrieved value.
	 * @throws IndexOutOfBoundsException if the position is out of the structure.
	 */
	public byte getUint8(int offset) {
		if (offset < 0 || offset >= this.length) {
			throw new IndexOutOfBoundsException();
		}
		int index = this.offset + offset;
		return this.buffer[index];
	}
	
	/**
	 * Retrieves an unsigned 16bit integer from this structure.
	 * @param offset the relative position to the start of the structure.
	 * @return retrieved value.
	 * @throws IndexOutOfBoundsException if the position is out of the structure.
	 */
	public int getUint16(int offset) {
		if (offset < 0 || offset + 2 > this.length) {
			throw new IndexOutOfBoundsException();
		}
		int index = this.offset + offset;
		int b1 = buffer[index] & 0xff;
		int b2 = buffer[index + 1] & 0xff;
		return (b2 << 8) | b1;
	}
	
	/**
	 * Retrieves an unsigned 32bit integer from this structure.
	 * @param offset the relative position to the start of the structure.
	 * @return retrieved value.
	 * @throws IndexOutOfBoundsException if the position is out of the structure.
	 */
	public long getUint32(int offset) {
		if (offset < 0 || offset + 4 > this.length) {
			throw new IndexOutOfBoundsException();
		}
		int index = this.offset + offset;
		long b1 = buffer[index] & 0xff;
		long b2 = buffer[index + 1] & 0xff;
		long b3 = buffer[index + 2] & 0xff;
		long b4 = buffer[index + 3] & 0xff;
		return (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
	}

	/**
	 * Retrieves a string from this structure.
	 * @param offset the relative position to the start of the structure.
	 * @param length the length of the string in this structure.
	 * @return retrieved value.
	 * @throws IndexOutOfBoundsException if the position is out of the structure.
	 */
	public String getString(int offset, int length) {
		return getString(offset, length, StandardCharsets.UTF_8);
	}

	/**
	 * Retrieves an encoded string from this structure.
	 * @param offset the relative position to the start of the structure.
	 * @param length the length of the string in this structure.
	 * @param charset the encoding of the string.
	 * @return retrieved value.
	 * @throws IndexOutOfBoundsException if the position is out of the structure.
	 */
	public String getString(int offset, int length, Charset charset) {
		if (offset < 0 || offset + length > this.length) {
			throw new IndexOutOfBoundsException();
		}
		int index = this.offset + offset;
		return new String(this.buffer, index, length, charset);
	}
}
