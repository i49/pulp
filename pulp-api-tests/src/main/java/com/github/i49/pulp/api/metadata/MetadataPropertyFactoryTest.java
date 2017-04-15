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

import java.util.Locale;

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

	/* newCoverage(String) */
	
	@Test
	public void newCoverage_shouldCreateCoverage() {
		String text = "17th century";
		Coverage p = factory.newCoverage(text);
		assertThat(p.getName()).isEqualTo("coverage");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newCoverage(String, Locale) */
	
	@Test
	public void newCoverage_shouldCreateCoverageWithLanguage() {
		String text = "17th century";
		Coverage p = factory.newCoverage(text, Locale.ENGLISH);
		assertThat(p.getName()).isEqualTo("coverage");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}

	/* newDescription(String) */
	
	@Test
	public void newDescription_shouldCreateDescription() {
		String text = "Illustrated guide to airport markings and lighting signals.";
		Description p = factory.newDescription(text);
		assertThat(p.getName()).isEqualTo("description");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}
	
	/* newDescription(String, Locale) */
	
	@Test
	public void newDescription_shouldCreateDescriptionWithLanguage() {
		String text = "Illustrated guide to airport markings and lighting signals.";
		Description p = factory.newDescription(text, Locale.ENGLISH);
		assertThat(p.getName()).isEqualTo("description");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}
	
	/* newIdentifier() */
	
	@Test
	public void newIdentifier_shouldCreateRandomIdentifier() {
		Identifier identifier = factory.newIdentifier();
		assertThat(identifier.getName()).isEqualTo("identifier");
		assertThat(identifier.getValue()).startsWith("urn:uuid:");
		assertThat(identifier.getScheme()).hasValue(IdentifierScheme.UUID);
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
		assertThat(identifier.getScheme()).hasValue(IdentifierScheme.ISBN);
		assertThat(identifier.getSchemeURI()).isEmpty();
	}
	
	/* newRelation(String) */
	
	@Test
	public void newRelation_shouldCreateRelation() {
		String text = "\"Two Lives\" [Resource is a collection of two novellas, one of which is \"Reading Turgenev\"]";
		Relation p = factory.newRelation(text);
		assertThat(p.getName()).isEqualTo("relation");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newRelation(String, Locale) */

	@Test
	public void newRelation_shouldCreateRelationWithLanguage() {
		String text = "\"Two Lives\" [Resource is a collection of two novellas, one of which is \"Reading Turgenev\"]";
		Relation p = factory.newRelation(text, Locale.ENGLISH);
		assertThat(p.getName()).isEqualTo("relation");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}
	
	/* newRighs(String) */
	
	@Test
	public void newRights_shouldCreateRights() {
		String text = "Access limited to members";
		Rights p = factory.newRights(text);
		assertThat(p.getName()).isEqualTo("rights");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newRighs(String, Locale) */

	@Test
	public void newRights_shouldCreateRightsWithLanguage() {
		String text = "Access limited to members";
		Rights p = factory.newRights(text, Locale.ENGLISH);
		assertThat(p.getName()).isEqualTo("rights");
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}
}
