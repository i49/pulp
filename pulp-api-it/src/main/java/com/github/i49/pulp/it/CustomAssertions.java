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

import java.util.Optional;

import org.assertj.core.api.AbstractAssert;

import com.github.i49.pulp.api.core.Spine;
import com.github.i49.pulp.api.core.Spine.Page;
import com.github.i49.pulp.api.vocabularies.Normalizable;
import com.github.i49.pulp.api.vocabularies.Property;

public class CustomAssertions {

	public static <T extends Property<?>> PropertyAssert<T> assertThat(T actual) {
		return new PropertyAssert<T>(actual);
	}

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
	
	public static class PropertyAssert<T extends Property<?>> extends AbstractAssert<PropertyAssert<T>, T> {
		
		private PropertyAssert(T actual) {
			super(actual, PropertyAssert.class);
		}

		public PropertyAssert<T> hasValue(String value) {
			isNotNull();
			String actualValue = actual.getValueAsString();
			if (!actualValue.equals(value)) {
				failWithMessage("Expected property value to be <%s> but was <%s>", value, actualValue);
			}
			return this;
		}

		public PropertyAssert<T> hasNormalizedValue(String value) {
			isNotNull();
			if (actual instanceof Normalizable) {
				Normalizable actual = (Normalizable)this.actual;
				Optional<String> normalized = actual.getNormalizedValue();
				if (!normalized.isPresent()) {
					failWithMessage("Expected normalized value to be <%s> but was <empty>", value);
				}
				String actualValue = normalized.get();
				if (!actualValue.equals(value)) {
					failWithMessage("Expected normalized value to be <%s> but was <%s>", value, actualValue);
				}
			} else {
				failWithMessage("Property does not have a normalized value");
			}
			return this;
		}
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
