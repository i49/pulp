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

import java.io.Closeable;
import java.io.IOException;

/**
 * A writer to write a publication.
 */
public interface PublicationWriter extends Closeable {
	
	/**
	 * Closes this writer and frees any resources associated with this writer.
	 * 
	 * @throws EpubException if an I/O error has occurred.
	 */
	void close();
	
	/**
	 * Writes a publication.
	 * 
	 * @param publication the publication to be written.
	 * @throws IllegalArgumentException if {@code publication} is {@code null}.
	 * @throws EpubException if the publication cannot be written due to some error such as {@link IOException}.
	 */
	void write(Publication publication);
}
