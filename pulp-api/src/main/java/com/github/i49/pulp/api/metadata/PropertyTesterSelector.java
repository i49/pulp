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

import com.github.i49.pulp.api.vocabularies.Term;

/**
 * Type to test selected property interface and return boolean value.
 */
public interface PropertyTesterSelector {
	
	boolean contributor();
	
	boolean coverage();
	
	boolean creator();
	
	boolean date();
	
	boolean description();
	
	boolean format();
	
	boolean identifier();

	boolean language();
	
	boolean modified();

	boolean publisher();

	boolean relation();
	
	boolean rights();
	
	boolean source();
	
	boolean subject();
	
	boolean title();
	
	boolean type();
	
	boolean propertyOf(Term term);
}
