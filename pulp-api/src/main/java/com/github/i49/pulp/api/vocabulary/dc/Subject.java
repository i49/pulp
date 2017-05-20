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

package com.github.i49.pulp.api.vocabulary.dc;

import java.net.URI;
import java.util.Optional;

import com.github.i49.pulp.api.vocabulary.PropertyBuilder;
import com.github.i49.pulp.api.vocabulary.Term;
import com.github.i49.pulp.api.vocabulary.TypedProperty;

/**
 *
 */
public interface Subject extends TypedProperty<String> {
	
	Optional<SubjectAuthority> getAuthority();
	
	Optional<String> getCode();

	Optional<URI> getScheme();
	
	@Override
	default Term getTerm() {
		return DublinCore.SUBJECT;
	}
	
	public interface Builder extends PropertyBuilder<String, Subject, Builder> {
		
		Builder ofCode(SubjectAuthority authority, String code);

		Builder ofCode(URI scheme, String code);
	}
}
