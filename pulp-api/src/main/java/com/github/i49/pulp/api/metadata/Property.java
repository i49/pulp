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

package com.github.i49.pulp.api.metadata;

/**
 * A property composing the metadata for EPUB publication and rendition.
 */
public interface Property {

	/**
	 * Returns the term of this property.
	 * 
	 * @return the term of this property.
	 */
	Term getTerm();
	
	/**
	 * Return the type of this property.
	 * 
	 * @return the type of this property.
	 */
	default PropertyType getType() {
		return getTerm().getType();
	}
	
	/**
	 * Returns the value of this property as string.
	 * 
	 * @return the string representation of the property value.
	 */
	String getValueAsString();
	
	/**
	 * Returns the string representation of this property.
	 * Equivalent to {@link #getValueAsString()}.
	 * 
	 * @return the string representation of this property.
	 */
	@Override
	String toString();
}
