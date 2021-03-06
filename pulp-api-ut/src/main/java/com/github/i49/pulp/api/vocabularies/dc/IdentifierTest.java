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

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.publication.Epub;

public class IdentifierTest {

	private Metadata m;
	
	private static final String ISBN = "urn:isbn:0451450523";
	
	@Before
	public void setUp() {
		m = Epub.createPublication().addRendition().getMetadata();
	}
		
	/* isPrimary() */

	@Test
	public void isPrimary_shouldReturnTrueIfMarkedAsPrimary() {
		m.add().identifier(ISBN).primary(true);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.isPrimary()).isTrue();
	}

	@Test
	public void isPrimary_shouldReturnFalseIfMarkedAsNotPrimary() {
		m.add().identifier(ISBN).primary(false);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.isPrimary()).isFalse();
	}

	@Test
	public void isPrimary_shouldReturnTrueByDefault() {
		m.add().identifier(ISBN);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.isPrimary()).isTrue();
	}
}
