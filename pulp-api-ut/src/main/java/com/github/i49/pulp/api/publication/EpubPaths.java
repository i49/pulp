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
import java.nio.file.Paths;

final class EpubPaths {
	
	private static final Path DEFAULT_BASE_PATH = Paths.get("target", "classes");
	private static final Path EPUB_PATH;
	private static final Path EXPANDED_PATH;

	static {
		Path base = determineResourcesPath();
		EPUB_PATH = base.resolve("epub");
		EXPANDED_PATH = base.resolve("expanded");
	}

	private static Path determineResourcesPath() {
		String basePath = System.getProperty("test.resources.path");
		if (basePath == null) {
			return DEFAULT_BASE_PATH;
		}
		return Paths.get(basePath);
	}
	
	public static Path get(String name) {
		if (name.endsWith(".epub")) {
			return EPUB_PATH.resolve(name);
		} else {
			return EXPANDED_PATH.resolve(name);
		}
	}
}
