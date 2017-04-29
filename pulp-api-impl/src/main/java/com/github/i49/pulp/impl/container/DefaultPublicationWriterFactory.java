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

package com.github.i49.pulp.impl.container;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import com.github.i49.pulp.api.core.EpubException;
import com.github.i49.pulp.api.core.PublicationWriter;
import com.github.i49.pulp.api.core.PublicationWriterFactory;
import com.github.i49.pulp.impl.base.Messages;

/**
 * The default implementation of {@link PublicationWriterFactory}.
 */
public class DefaultPublicationWriterFactory implements PublicationWriterFactory {

	public DefaultPublicationWriterFactory() {
	}
	
	@Override
	public PublicationWriter createWriter(Path path) {
		checkNotNull(path, "path");
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
		checkNotNull(stream, "stream");
		return new EpubPublicationWriter3(new WriteableZipContainer(stream));
	}
}
