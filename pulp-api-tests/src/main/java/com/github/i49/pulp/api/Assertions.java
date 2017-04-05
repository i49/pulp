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

import org.assertj.core.api.AbstractThrowableAssert;

/**
 * Custom assertions.
 */
public final class Assertions {

	/**
	 * Provides assertion on an instance of {@link EpubParsingExceptionAssert}. 
	 * 
	 * @param actual the actual instance of {@link EpubParsingExceptionAssert}. 
	 * @return created assertion.
	 */
	public static EpubParsingExceptionAssert assertThat(EpubParsingException actual) {
		return new EpubParsingExceptionAssert(actual);
	}
 	
	private Assertions() {
	}

	public static class EpubParsingExceptionAssert extends AbstractThrowableAssert<EpubParsingExceptionAssert, EpubParsingException> {

		public EpubParsingExceptionAssert(EpubParsingException actual) {
			super(actual, EpubParsingExceptionAssert.class);
		}
		
		public EpubParsingExceptionAssert hasLocation(String expected) {
			isNotNull();
			String location = actual.getLocation();
			if (!expected.equals(location)) {
				failWithMessage("Expected location to be <%s>, but was <%s>", expected, location);
			}
			return this;
		}
		
		public EpubParsingExceptionAssert hasContainerPath(Path expected) {
			isNotNull();
			Path path = actual.getContainerPath();
			if (!expected.equals(path)) {
				failWithMessage("Expected path to be <%s>, but was <%s>", expected, path);
			}
			return this;
		}
	}
}
