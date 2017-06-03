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

package com.github.i49.pulp.api.vocabularies.rendering;

import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.Term;

/**
 * Specifies whether the given rendition is reflowable or pre-paginated.
 */
public enum Layout implements Property<String> {
	/**
	 * The given Rendition is pre-paginated. 
	 */
	PRE_PAGINATED,
	/**
	 * The given Rendition is not pre-paginated.
	 */
	REFLOWABLE,
	;
	
	private final String value;
	
	private Layout() {
		this.value = Property.valueOf(this);
	}

	@Override
	public Term getTerm() {
		return Rendering.LAYOUT;
	}

	@Override
	public String getValue() {
		return value;
	}
}
