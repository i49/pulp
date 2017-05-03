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
import java.io.OutputStream;
import java.nio.file.Path;

public abstract class WriteableContainer extends AbstractContainer {
	
	protected WriteableContainer(Path path) {
		super(path);
	}

	public abstract void writeItem(String location, byte[] content) throws IOException;
	
	public abstract OutputStream openItemToWrite(String location) throws IOException;
}