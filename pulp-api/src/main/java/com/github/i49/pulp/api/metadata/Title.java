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

import java.util.Optional;

/**
 * A metadata property representing an instance of a name given to the EPUB publication.
 * 
 * @see <a href="http://dublincore.org/documents/dces/#title">Dublin Core Metadata Element Set, Version 1.1</a> 
 */
public interface Title extends TextProperty {

	/**
	 * Returns the alternative representation of this property.
	 * 
	 * @return the alternative representation of this property.
	 */
	Optional<Alternative> getAlternative();

	/**
	 * Returns the normalized value of this property used when the value is filed.
	 * 
	 * @return the normalized value of this property.
	 */
	Optional<String> getNormalizedValue();
}
