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

import java.io.IOException;
import java.util.Optional;

import org.assertj.core.api.AbstractAssert;

import com.github.i49.pulp.api.Spine.Page;

/**
 * Custom assertions.
 */
public final class EpubAssertions {

	/**
	 * Provides assertion on an instance of {@link Manifest}. 
	 * @param actual the actual instance of {@link Manifest}. 
	 * @return created assertion.
	 */
	public static ManifestAssert assertThat(Manifest actual) {
		return new ManifestAssert(actual);
	}

	/**
	 * Provides assertion on an instance of {@link Spine}. 
	 * @param actual the actual instance of {@link Spine}. 
	 * @return created assertion.
	 */
	public static SpineAssert assertThat(Spine actual) {
		return new SpineAssert(actual);
	}
	
	private EpubAssertions() {
	}

	public static class ManifestAssert extends AbstractAssert<ManifestAssert, Manifest> {

		private ManifestAssert(Manifest actual) {
			super(actual, ManifestAssert.class);
		}

		public ManifestAssert hasItems(int expected) {
			isNotNull();
			if (actual.getNumberOfItems() != expected) {
				failWithMessage("Expected nubmer of items to be <%d>, but was <%d>", expected, actual.getNumberOfItems());
			}
			return this;
		}
		
		public ManifestAssert hasResource(String location, int length) {
			isNotNull();
			Optional<Manifest.Item> item = actual.find(location);
			if (!item.isPresent()) {
				failWithMessage("Manifest did not contain the resource at <%s>", location);
			}
			PublicationResource resource = item.get().getResource();
			try {
				byte[] content = resource.getContent();
				if (content.length != length) {
					failWithMessage("Expected content length to be <%d>, but was <%d>", length, content.length);
				}
			} catch (IOException e) {
				failWithMessage("Failed to load content of resource at <%s>", location);
			}
			return this;
		}
	}

	public static class SpineAssert extends AbstractAssert<SpineAssert, Spine> {

		private SpineAssert(Spine actual) {
			super(actual, SpineAssert.class);
		}
		
		public SpineAssert hasPages(int expected) {
			isNotNull();
			if (actual.getNumberOfPages() != expected) {
				failWithMessage("Expected nubmer of pages to be <%d>, but was <%d>", expected, actual.getNumberOfPages());
			}
			return this;
		}
		
		public SpineAssert linearExcept(int... indices) {
			isNotNull();

			int next = 0;
			for (int i = 0; i < actual.getNumberOfPages(); i++) {
				Page page = actual.get(i);
				if (next < indices.length && i == indices[next]) {
					if (page.isLinear()) {
						failWithMessage("Page at %d must be non-linear", i);
					}
					next++;
				} else {
					if (!page.isLinear()) {
						failWithMessage("Page at %d must be linear", i);
					}
				}
			}
			
			return this;
		}
	}
}
