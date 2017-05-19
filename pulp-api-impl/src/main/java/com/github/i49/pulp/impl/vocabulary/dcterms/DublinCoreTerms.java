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

package com.github.i49.pulp.impl.vocabulary.dcterms;

import com.github.i49.pulp.api.vocabulary.dcterms.DublinCoreTerm;
import com.github.i49.pulp.api.vocabulary.dcterms.Modified;
import com.github.i49.pulp.impl.vocabulary.DateProperty;

/**
 */
public final class DublinCoreTerms {

	private DublinCoreTerms() {
	}
	
	public static Modified.Builder modified() {
		return new ModifiedBuilder();
	}

	private static class DefaultModified extends DateProperty implements Modified {
		
		private DefaultModified(ModifiedBuilder b) {
			super(DublinCoreTerm.MODIFIED, b);
		}
	}
	
	private static class ModifiedBuilder extends DateProperty.Builder<Modified, Modified.Builder>
		implements Modified.Builder {
		
		@Override
		protected Modified build() {
			return new DefaultModified(this);
		}
	}
}
