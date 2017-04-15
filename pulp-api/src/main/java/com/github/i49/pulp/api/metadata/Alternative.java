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

import java.util.Locale;

/**
 * An alternative representation of a metadata property value.
 * This instance can attached to the following metadata properties.
 * <ul>
 * <li>{@link Creator}</li>
 * <li>{@link Contributor}</li>
 * <li>{@link Publisher}</li>
 * <li>{@link Title}</li>
 * </ul>
 */
public interface Alternative {

	/**
	 * Returns the alternative representation of the property.
	 * 
	 * @return the alternative representation.
	 */
	String getValue();
	
	/**
	 * Returns the language used for the alternative representation.
	 * 
	 * @return the language used for the alternative representation.
	 */
	Locale getLanguage();
}
