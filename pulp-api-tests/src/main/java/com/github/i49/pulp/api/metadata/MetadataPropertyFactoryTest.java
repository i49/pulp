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

import com.github.i49.pulp.api.Epub;

/**
 * Unit tests for {@link MetadataPropertyFactory}.
 */
public class MetadataPropertyFactoryTest {
	
	private MetadataPropertyFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createMetadataPropertyFactory();
	}

	/* newIdentifier() */
	
	@Test
	public void newIdentifier_shouldCreateRandomIdentifier() {
		Identifier identifier = factory.newIdentifier();
		assertThat(identifier.getName()).isEqualTo("identifier");
		assertThat(identifier.getValue()).startsWith("urn:uuid:");
		assertThat(identifier.getScheme()).contains(IdentifierScheme.UUID);
		assertThat(identifier.getSchemeURI()).isEmpty();
	}

	/* newIdentifier(String) */
	
	@Test
	public void newIdentifier_shouldCreateIdentifierWithValue() {
		String value = "urn:isbn:0451450523";
		Identifier identifier = factory.newIdentifier(value);
		assertThat(identifier.getName()).isEqualTo("identifier");
		assertThat(identifier.getValue()).isEqualTo(value);
		assertThat(identifier.getScheme()).isEmpty();
		assertThat(identifier.getSchemeURI()).isEmpty();
	}

	/* newIdentifier(String, IdentifierScheme) */
	
	@Test
	public void newIdentifier_shouldCreateIdentifierWithValueAndScheme() {
		String value = "urn:isbn:0451450523";
		Identifier identifier = factory.newIdentifier(value, IdentifierScheme.ISBN);
		assertThat(identifier.getName()).isEqualTo("identifier");
		assertThat(identifier.getValue()).isEqualTo(value);
		assertThat(identifier.getScheme()).contains(IdentifierScheme.ISBN);
		assertThat(identifier.getSchemeURI()).isEmpty();
	}
}
