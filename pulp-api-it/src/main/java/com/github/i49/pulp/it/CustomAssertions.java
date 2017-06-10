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

package com.github.i49.pulp.it;

import org.assertj.core.api.AbstractAssert;

import com.github.i49.pulp.api.publication.Spine;
import com.github.i49.pulp.api.publication.Spine.Page;

public class CustomAssertions {

	/**
	 * Provides assertion on an instance of {@link Spine}. 
	 * 
	 * @param actual the actual instance of {@link Spine}. 
	 * @return created assertion.
	 */
	public static SpineAssert assertThat(Spine actual) {
		return new SpineAssert(actual);
	}
	
	private CustomAssertions() {
	}
	
	public static class SpineAssert extends AbstractAssert<SpineAssert, Spine> {

		private SpineAssert(Spine actual) {
			super(actual, SpineAssert.class);
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
