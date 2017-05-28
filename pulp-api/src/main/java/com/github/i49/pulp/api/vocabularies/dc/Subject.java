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

import java.net.URI;
import java.util.Optional;

import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.PropertyBuilder;

/**
 * A topic of the resource.
 * This property represents the term of {@link DublinCore#SUBJECT}.
 */
public interface Subject extends Property<String> {
	
	/**
	 * Returns the authority responsible for the subject code.
	 * 
	 * @return one of authorities defined in {@link SubjectAuthority}, may be empty.
	 */
	Optional<SubjectAuthority> getAuthority();
	
	/**
	 * Returns the code of this subject.
	 * 
	 * @return the code of this subject, may be empty.
	 */
	Optional<String> getCode();

	/**
	 * Returns the URI representing the scheme of the subject code.
	 * 
	 * @return the scheme URI of the subject code, may be empty.
	 */
	Optional<URI> getScheme();
	
	/**
	 * Builder for building instances of {@link Subject}.
	 */
	public interface Builder extends PropertyBuilder<String, Subject, Builder> {
		
		/**
		 * Specifies the code of the subject and its authority.
		 * Calling this method clears the scheme already specified.
		 * 
		 * @param authority one of authorities defined in {@link SubjectAuthority}, which is responsible for the code.
		 * @param code the code of the subject.
		 * @return this builder.
		 * @throws IllegalArgumentException if one or more parameters are invalid.
		 */
		Builder ofCode(SubjectAuthority authority, String code);

		/**
		 * Specifies the code of the subject and its scheme.
		 * Calling this method clears the authority already specified.
		 * 
		 * @param scheme the URI representing the scheme of the subject code.
		 * @param code the code of the subject.
		 * @return this builder.
		 * @throws IllegalArgumentException if one or more parameters are invalid.
		 */
		Builder ofCode(URI scheme, String code);
	}
}
