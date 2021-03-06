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

package com.github.i49.pulp.impl.io.readers;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipException;

import com.github.i49.pulp.api.publication.EpubException;
import com.github.i49.pulp.api.publication.EpubService;
import com.github.i49.pulp.api.publication.PublicationReader;
import com.github.i49.pulp.api.publication.PublicationReaderFactory;
import com.github.i49.pulp.impl.base.Messages;
import com.github.i49.pulp.impl.io.containers.ReadableContainer;
import com.github.i49.pulp.impl.io.containers.ReadableZipContainer;

/**
 * The default implementation of {@link PublicationReaderFactory}.
 */
public class DefaultPublicationReaderFactory implements PublicationReaderFactory {

	private final EpubService service;
	
	/**
	 * Constructs this factory.
	 * 
	 * @param service the EPUB service.
	 */
	public DefaultPublicationReaderFactory(EpubService service) {
		assert(service != null);
		this.service = service;
	}
	
	@Override
	public PublicationReader createReader(Path path) {
		checkNotNull(path, "path");
		ReadableContainer container = openContainer(path);
		return new EpubPublicationReader(container, this.service);
	}
	
	/**
	 * Opens the abstract container at the specified path for reading.
	 * 
	 * @param path the path where the container exists.
	 * @return opened abstract container.
	 * @throws EpubException if a problem has occurred while opening the container.
	 */
	private ReadableContainer openContainer(Path path) {
		try {
			long size = Files.size(path);
			if (size == 0) {
				throw new EpubException(Messages.CONTAINER_EMPTY(path));
			}
			ReadableContainer container = new ReadableZipContainer(path);
			container.validate();
			return container;
		} catch (FileNotFoundException e) {
			throw new EpubException(Messages.CONTAINER_NOT_FOUND(path), e);
		} catch (ZipException e) {
			throw new EpubException(Messages.CONTAINER_CORRUPT(path), e);
		} catch (IOException e) {
			throw new EpubException(Messages.CONTAINER_IO_FAILURE(path), e);
		}
	}
}
