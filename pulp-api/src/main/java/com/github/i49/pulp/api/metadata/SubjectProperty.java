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

import java.net.URI;
import java.util.Optional;

/**
 * The topic of the EPUB rendition.
 * This property can be used for {@link DublinCore#SUBJECT} and others.
 */
public interface SubjectProperty extends TypedProperty<String> {
	
	/**
	 * Returns the authority of this subject.
	 * 
	 * @return the authority of this subject.
	 */
	Optional<SubjectAuthority> getAuthority();
	
	/**
	 * Returns the URI representing the authority of this subject.
	 * 
	 * @return the URI representing the authority of this subject.
	 */
	Optional<URI> getScheme();
	
	/**
	 * Returns the code of this subject defined in the authority.
	 * 
	 * @return the code of this subject.
	 */
	Optional<String> getCode();
	
	/**
	 * Clears the authority, scheme and code assigned.
	 * 
	 * @return this property.
	 */
	SubjectProperty resetCode();
	
	/**
	 * Assigns the authority and the code define by the authority.
	 * 
	 * @param authority the authority of this subject.
	 * @param code the code of this subject defined by the authority.
	 * @return this property.
	 * @throws IllegalArgumentException if any parameters are invalid.
	 */
	SubjectProperty setCode(SubjectAuthority authority, String code);

	/**
	 * Assigns the scheme and the code define in the scheme.
	 * 
	 * @param scheme the URI which represents the scheme of the subject.
	 * @param code the code of this subject defined by the scheme.
	 * @return this property.
	 * @throws IllegalArgumentException if any parameters are invalid.
	 */
	SubjectProperty setCode(URI scheme, String code);
	
	/**
	 * {@inheritDoc}
	 */
	SubjectProperty setValue(String value);
}
