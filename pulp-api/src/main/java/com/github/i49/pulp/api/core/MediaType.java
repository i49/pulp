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

package com.github.i49.pulp.api.core;

/**
 * The MIME media type used to specify the type and the format of {@link PublicationResource}.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2046">RFC 2046</a>
 */
public interface MediaType {

	/**
	 * Returns the type part of this media type.
	 * @return the type part of this media type. 
	 */
	String getType();
	
	/**
	 * Returns the subtype part of this media type.
	 * @return the subtype part of this media type. 
	 */
	String getSubtype();
	
	/**
	 * Returns the string representation of this media type.
	 * @return the string representation of this media type.
	 */
	String toString();
}
