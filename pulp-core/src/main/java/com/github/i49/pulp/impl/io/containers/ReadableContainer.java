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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import com.github.i49.pulp.api.publication.ContentSource;

/**
 * Abstract Container interface to be read.
 */
public abstract class ReadableContainer extends AbstractContainer {

	protected ReadableContainer(Path path) {
		super(path);
	}

	/**
	 * Validates the format of this container.
	 * 
	 * @throws EpubException if the format of this container has any problems.
	 * @throws IOException if I/O error has occurred while reading the item.
	 */
	public abstract void validate() throws IOException;
	
	public abstract boolean contains(String location);
	
	/**
	 * Reads an item from this container.
	 * 
	 * @param location the location of the item in the container.
	 * @return bytes read.
	 * @throws IOException if I/O error has occurred while reading the item.
	 */
	public abstract byte[] readItem(String location) throws IOException;
	
	public abstract InputStream openItemToRead(String location) throws IOException;
	
	public abstract ContentSource getContentSource(String location);
}
