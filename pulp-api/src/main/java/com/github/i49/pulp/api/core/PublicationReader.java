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

package com.github.i49.pulp.api.core;

import java.io.Closeable;
import java.io.IOException;

public interface PublicationReader  extends Closeable {

	/**
	 * Closes this reader and frees any resources associated with this reader.
	 * 
	 * @throws EpubException if an I/O error has occurred.
	 */
	@Override
	void close();
	
	/**
	 * Reads a publication.
	 * 
	 * @return the publication read by this reader.
	 * @throws EpubException if the publication cannot be read due to some error such as {@link IOException}.
	 */
	Publication read();
}
