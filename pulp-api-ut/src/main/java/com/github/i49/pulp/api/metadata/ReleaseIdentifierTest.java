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

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.publication.Epub;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;

public class ReleaseIdentifierTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		m = Epub.createPublication().addRendition().getMetadata();
	}

	@Test
	public void getUniqueIdentifier_shouldReturnEmptyByDefault() {
		ReleaseIdentifier ri = m.getReleaseIdentifier();
		assertThat(ri).isNotNull();
		assertThat(ri.getUniqueIdentifier()).isEmpty();
	}
	
	@Test
	public void getUniqueIdentifier_shouldReturnValidIdentifier() {
		Identifier p = m.add().identifier().result();
		ReleaseIdentifier ri = m.getReleaseIdentifier();
		assertThat(ri.getUniqueIdentifier()).hasValue(p);
	}
	
	@Test
	public void getUniqueIdentifier_shouldReturnFirstPrimaryIdentifier() {
		@SuppressWarnings("unused")
		Identifier p1 = m.add().identifier().primary(false).result();
		Identifier p2 = m.add().identifier().primary(true).result();
		@SuppressWarnings("unused")
		Identifier p3 = m.add().identifier().primary(true).result();
		ReleaseIdentifier ri = m.getReleaseIdentifier();
		assertThat(ri.getUniqueIdentifier()).hasValue(p2);
	}
}
