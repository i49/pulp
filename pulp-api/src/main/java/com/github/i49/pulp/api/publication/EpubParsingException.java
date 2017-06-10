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

package com.github.i49.pulp.api.publication;

import java.nio.file.Path;

/**
 * {@link EpubException} that will be thrown when parsing EPUB.
 */
public class EpubParsingException extends EpubException {

	private static final long serialVersionUID = 1L;

	private final String location;
	private final Path containerPath;
	
	/**
	 * Constructs this exception.
	 * @param message the detail message.
	 * @param location the location of the entry in the container that caused the problem.
	 * @param containerPath the path of the container.
	 */
	public EpubParsingException(String message, String location, Path containerPath) {
		super(message);
		this.location = location;
		this.containerPath = containerPath;
	}
	
	/**
	 * Constructs this exception.
	 * @param message the detail message.
	 * @param cause the real cause of this exception.
	 * @param location the location of the entry in the container that caused this problem.
	 * @param containerPath the path of the container.
	 */
	public EpubParsingException(String message, Throwable cause, String location, Path containerPath) {
		super(message, cause);
		this.location = location;
		this.containerPath = containerPath;
	}
	
	/**
	 * Return the location of the entry in the container that caused this problem.
	 * @return the location of the entry.
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Returns the path of the container being parsed.
	 * @return the path of the container.
	 */
	public Path getContainerPath() {
		return containerPath;
	}
}
