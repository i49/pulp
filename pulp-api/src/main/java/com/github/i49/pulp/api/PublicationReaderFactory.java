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

package com.github.i49.pulp.api;

import java.nio.file.Path;

/**
 * A factory type for creating instances of {@link PublicationReader}.
 */
public interface PublicationReaderFactory {

	/**
	 * Creates an instance of {@link PublicationReader}.
	 * @param path the location where the file to be read is located.
	 * @return created instance of {@link PublicationReader}.
	 * @exception IllegalArgumentException if given {@code path} is {@code null}.
	 * @exception EpubException if an I/O error has occurred.
	 */
	PublicationReader createReader(Path path);
}
