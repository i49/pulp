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
 * The rendering property which specifies the intended Reading System 
 * synthetic spread behavior for the given rendition.
 */
public enum Spread implements Property<String> {
	/**
	 * No explicit Synthetic Spread behavior is defined.
	 */
	AUTO("auto"),
	/**
	 * Reading Systems should render a Synthetic Spread 
	 * regardless of device orientation.
	 */
	BOTH("both"),
	/**
	 * Reading Systems should render a Synthetic Spread for spine items 
	 * only when the device is in landscape orientation. 
	 */
	LANDSCAPE("landscape"),
	/**
	 * Reading Systems must not incorporate spine items in a Synthetic Spread.
	 */
	NONE("none"),
	;

	private final String value;
	
	private Spread(String value) {
		this.value = value;
	}

	@Override
	public Term getTerm() {
		return Rendering.SPREAD;
	}

	@Override
	public String getValue() {
		return value;
	}
}
