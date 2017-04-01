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

package com.github.i49.pulp.core.container;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import com.github.i49.pulp.api.EpubException;
import com.github.i49.pulp.api.PublicationWriter;
import com.github.i49.pulp.api.PublicationWriterFactory;
import com.github.i49.pulp.core.Messages;

/**
 * An implementation of {@link PublicationWriterFactory}.
 */
public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	public PublicationWriterFactoryImpl() {
	}
	
	@Override
	public PublicationWriter createWriter(Path path) {
		if (path == null) {
			throw new IllegalArgumentException("path is null");
		}
		WriteableContainer container = null;
		try {
			container = new WriteableZipContainer(path);
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_IO_FAILURE(path), e);
		}
		return new EpubPublicationWriter3(container);
	}

	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		if (stream == null) {
			throw new IllegalArgumentException("stream is null");
		}
		return new EpubPublicationWriter3(new WriteableZipContainer(stream));
	}
}
