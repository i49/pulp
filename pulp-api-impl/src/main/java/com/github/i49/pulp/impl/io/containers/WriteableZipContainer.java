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

package com.github.i49.pulp.impl.io.containers;

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
