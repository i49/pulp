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

package com.github.i49.pulp.api.vocabularies.dc;

import com.github.i49.pulp.api.vocabularies.Text;

/**
 * Information about rights held in and over the resource.
 * This property represents the term of {@link DublinCore#RIGHTS}.
 */
public interface Rights extends Text {

	/**
	 * Builder for building instances of {@link Rights}.
	 */
	public interface Builder extends Text.Builder<Rights, Builder> {
	}
}
