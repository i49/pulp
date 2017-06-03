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
 * The rendering property which specifies which orientation 
 * the author intends the given rendition to be rendered in. 
 */
public enum Orientation implements Property<String> {
	/**
	 * The given rendition is not orientation constrained. 
	 */
	AUTO,
	/**
	 * The given rendition is intended for landscape rendering. 
	 */
	LANDSCAPE,
	/**
	 * The given rendition is intended for portrait rendering. 
	 */
	PORTRAIT,
	;

	private final String value;
	
	private Orientation() {
		this.value = Property.valueOf(this);
	}
	
	@Override
	public Term getTerm() {
		return Rendering.ORIENTATION;
	}

	@Override
	public String getValue() {
		return value;
	}
}
