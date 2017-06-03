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

package com.github.i49.pulp.api.vocabularies;

/**
 * The base type of all types of properties.
 * 
 * @param <V> the type of the property value.
 */
public interface Property<V> {
	
	/**
	 * Returns the term of this property.
	 * 
	 * @return the term of this property.
	 */
	Term getTerm();

	/**
	 * Returns the value of this property.
	 * 
	 * @return the value of this property.
	 */
	V getValue();
	
	/**
	 * Returns the string representation of this property value.
	 * 
	 * @return the string representation of this property value.
	 */
	default String getValueAsString() {
		return getValue().toString();
	}
	
	/**
	 * Returns the string representation of this property.
	 * 
	 * @return the string representation of this property.
	 */
	@Override
	String toString();
	
	/**
	 * Utility method to generate a property value from an enum constant.
	 * 
	 * @param <E> the type of the enumeration.
	 * @param element the member of the enumeration E.
	 * @return generated property value. 
	 */
	static <E extends Enum<E>> String valueOf(E element) {
		return element.name().replaceAll("_", "-").toLowerCase();
	}
}
