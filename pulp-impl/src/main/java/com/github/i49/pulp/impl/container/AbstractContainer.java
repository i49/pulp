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

import java.io.Closeable;
import java.nio.file.Path;

/**
 * The OCF Abstract Container that defines a file system model
 * for the contents of the OCF ZIP Container. 
 */
public abstract class AbstractContainer implements Closeable {
	
	public static final String MIMETYPE_LOCATION = "mimetype";
	public static final String CONTAINER_DOCUMENT_LOCATION = "META-INF/container.xml";

	private final Path path;

	/**
	 * Constructs this container.
	 * @param path the location of this container. can be {@code null}.
	 */
	protected AbstractContainer(Path path) {
		this.path = path;
	}
	
	/**
	 * Returns the path of this container.
	 * @return the path of this container.
	 */
	public Path getPath() {
		return path;
	}
}
