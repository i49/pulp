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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractListAssert;

import com.github.i49.pulp.api.core.Spine;
import com.github.i49.pulp.api.core.Spine.Page;
import com.github.i49.pulp.api.metadata.Property;

public class Assertions {

	public static PropertyAssert assertThat(Property actual) {
		return new PropertyAssert(actual);
	}

	public static PropertyListAssert assertThat(List<Property> actual) {
		return new PropertyListAssert(actual);
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
	
	private Assertions() {
	}
	
	public static class PropertyAssert extends AbstractAssert<PropertyAssert, Property> {
		
		private PropertyAssert(Property actual) {
			super(actual, PropertyAssert.class);
		}

		public PropertyAssert hasValue(String value) {
			isNotNull();
			if (!actual.getValue().equals(value)) {
				failWithMessage("Expected property value to be <%s> but was <%s>", value, actual.getValue());
			}
			return this;
		}

		public PropertyAssert hasNormalizedValue(String value) {
			isNotNull();
			Optional<String> container = actual.getNormalizedValue();
			if (!container.isPresent()) {
				failWithMessage("Expected normalized value to be <%s> but was <empty>", value);
			}
			String actualValue = container.get();
			if (!actualValue.equals(value)) {
				failWithMessage("Expected normalized value to be <%s> but was <%s>", value, actualValue);
			}
			return this;
		}
	}
	
	public static class PropertyListAssert extends AbstractListAssert<PropertyListAssert, List<Property>, Property, PropertyAssert> {

		private PropertyListAssert(List<Property> actual) {
			super(actual, PropertyListAssert.class);
		}
		
		public PropertyListAssert containsExactly(String... values) {
			isNotNull();
			List<String> actualValues = actual.stream().map(Property::getValue).collect(Collectors.toList());
			org.assertj.core.api.Assertions.assertThat(actualValues).containsExactly(values);
			return this;
		}
		
		@Override
		protected PropertyAssert toAssert(Property value, String description) {
			PropertyAssert assertion = new PropertyAssert(value);
			return assertion;
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
