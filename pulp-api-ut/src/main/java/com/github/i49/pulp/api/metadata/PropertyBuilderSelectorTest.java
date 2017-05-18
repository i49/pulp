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

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;

/**
 *
 */
public class PropertyBuilderSelectorTest {

	private Metadata m;
	
	private static final String UUID = "urn:uuid:6e8bc430-9c3a-11d9-9669-0800200c9a66";
	
	@Before
	public void setUp() {
		Publication p = Epub.createPublication();
		Rendition r = p.addRendition();
		m = r.getMetadata();
	}

	@Test
	public void identifier_shouldGenerateIdentifier() {
		m.add().identifier();
		assertThat(m.find().identifier()).hasSize(1);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).startsWith("urn:uuid:");
	}
	
	@Test
	public void identifier_shouldBuildIdentifierWithSpecifiedValue() {
		m.add().identifier(UUID);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).isEqualTo(UUID);
	}

	@Test
	public void identifier_shouldBuildIdentifierWithSpecifiedScheme() {
		m.add().identifier(UUID).scheme(Identifier.Scheme.UUID);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).isEqualTo(UUID);
		assertThat(p.getScheme()).hasValue(Identifier.Scheme.UUID);
		assertThat(p.getSchemeURI()).isEmpty();
	}
}
