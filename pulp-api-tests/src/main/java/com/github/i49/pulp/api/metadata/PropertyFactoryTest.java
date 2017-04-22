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

import com.github.i49.pulp.api.core.Epub;

/**
 * Unit tests for {@link PropertyFactory}.
 */
public class PropertyFactoryTest {
	
	private PropertyFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createMetadataPropertyFactory();
	}

	/* newContributor(String) */
	
	@Test
	public void newContributor_shouldCreateContributor() {
		String name = "John Smith";
		Contributor p = factory.newContributor(name);
		assertThat(p.getTerm()).isSameAs(BasicTerm.CONTRIBUTOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEqualTo(name);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}

	/* newContributor(String, Locale) */

	@Test
	public void newContributor_shouldCreateContributorWithLanguage() {
		String name = "Hans Schmidt";
		Contributor p = factory.newContributor(name, Locale.GERMAN);
		assertThat(p.getTerm()).isSameAs(BasicTerm.CONTRIBUTOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEqualTo(name);
		assertThat(p.getLanguage()).hasValue(Locale.GERMAN);
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}
	
	/* newCoverage(String) */
	
	@Test
	public void newCoverage_shouldCreateCoverage() {
		String text = "17th century";
		Coverage p = factory.newCoverage(text);
		assertThat(p.getTerm()).isSameAs(BasicTerm.COVERAGE);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newCoverage(String, Locale) */
	
	@Test
	public void newCoverage_shouldCreateCoverageWithLanguage() {
		String text = "17th century";
		Coverage p = factory.newCoverage(text, Locale.ENGLISH);
		assertThat(p.getTerm()).isSameAs(BasicTerm.COVERAGE);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}

	/* newCreator(String) */
	
	@Test
	public void newCreator_shouldCreateCreator() {
		String name = "John Smith";
		Creator p = factory.newCreator(name);
		assertThat(p.getTerm()).isSameAs(BasicTerm.CREATOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEqualTo(name);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}

	/* newCreator(String, Locale) */

	@Test
	public void newCreator_shouldCreateCreatorWithLanguage() {
		String name = "Hans Schmidt";
		Creator p = factory.newCreator(name, Locale.GERMAN);
		assertThat(p.getTerm()).isSameAs(BasicTerm.CREATOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEqualTo(name);
		assertThat(p.getLanguage()).hasValue(Locale.GERMAN);
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}
	
	/* newDescription(String) */
	
	@Test
	public void newDescription_shouldCreateDescription() {
		String text = "Illustrated guide to airport markings and lighting signals.";
		Description p = factory.newDescription(text);
		assertThat(p.getTerm()).isSameAs(BasicTerm.DESCRIPTION);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}
	
	/* newDescription(String, Locale) */
	
	@Test
	public void newDescription_shouldCreateDescriptionWithLanguage() {
		String text = "Illustrated guide to airport markings and lighting signals.";
		Description p = factory.newDescription(text, Locale.ENGLISH);
		assertThat(p.getTerm()).isSameAs(BasicTerm.DESCRIPTION);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}
	
	/* newIdentifier() */
	
	@Test
	public void newIdentifier_shouldCreateRandomIdentifier() {
		Identifier identifier = factory.newIdentifier();
		assertThat(identifier.getTerm()).isSameAs(BasicTerm.IDENTIFIER);
		assertThat(identifier.getValue()).startsWith("urn:uuid:");
		assertThat(identifier.getScheme()).hasValue(IdentifierScheme.UUID);
		assertThat(identifier.getSchemeURI()).isEmpty();
	}

	/* newIdentifier(String) */
	
	@Test
	public void newIdentifier_shouldCreateIdentifierWithValue() {
		String value = "urn:isbn:0451450523";
		Identifier identifier = factory.newIdentifier(value);
		assertThat(identifier.getTerm()).isSameAs(BasicTerm.IDENTIFIER);
		assertThat(identifier.getValue()).isEqualTo(value);
		assertThat(identifier.getScheme()).isEmpty();
		assertThat(identifier.getSchemeURI()).isEmpty();
	}

	/* newIdentifier(String, IdentifierScheme) */
	
	@Test
	public void newIdentifier_shouldCreateIdentifierWithValueAndScheme() {
		String value = "urn:isbn:0451450523";
		Identifier identifier = factory.newIdentifier(value, IdentifierScheme.ISBN);
		assertThat(identifier.getTerm()).isSameAs(BasicTerm.IDENTIFIER);
		assertThat(identifier.getValue()).isEqualTo(value);
		assertThat(identifier.getScheme()).hasValue(IdentifierScheme.ISBN);
		assertThat(identifier.getSchemeURI()).isEmpty();
	}
	
	/* newPublisher(String) */
	
	@Test
	public void newPublisher_shouldCreatePublisher() {
		String name = "O’Reilly Media, Inc.";
		Publisher p = factory.newPublisher(name);
		assertThat(p.getTerm()).isSameAs(BasicTerm.PUBLISHER);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEqualTo(name);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).hasValue("pbl");
	}

	/* newPublisher(String, Locale) */

	@Test
	public void newPublisher_shouldCreatePublisherWithLanguage() {
		String name = "Lagardère Publishing";
		Publisher p = factory.newPublisher(name, Locale.FRENCH);
		assertThat(p.getTerm()).isSameAs(BasicTerm.PUBLISHER);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEqualTo(name);
		assertThat(p.getLanguage()).hasValue(Locale.FRENCH);
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).hasValue("pbl");
	}
	
	/* newRelation(String) */
	
	@Test
	public void newRelation_shouldCreateRelation() {
		String text = "\"Two Lives\" [Resource is a collection of two novellas, one of which is \"Reading Turgenev\"]";
		Relation p = factory.newRelation(text);
		assertThat(p.getTerm()).isSameAs(BasicTerm.RELATION);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newRelation(String, Locale) */

	@Test
	public void newRelation_shouldCreateRelationWithLanguage() {
		String text = "\"Two Lives\" [Resource is a collection of two novellas, one of which is \"Reading Turgenev\"]";
		Relation p = factory.newRelation(text, Locale.ENGLISH);
		assertThat(p.getTerm()).isSameAs(BasicTerm.RELATION);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}
	
	/* newRighs(String) */
	
	@Test
	public void newRights_shouldCreateRights() {
		String text = "Access limited to members";
		Rights p = factory.newRights(text);
		assertThat(p.getTerm()).isSameAs(BasicTerm.RIGHTS);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newRighs(String, Locale) */

	@Test
	public void newRights_shouldCreateRightsWithLanguage() {
		String text = "Access limited to members";
		Rights p = factory.newRights(text, Locale.ENGLISH);
		assertThat(p.getTerm()).isSameAs(BasicTerm.RIGHTS);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).hasValue(Locale.ENGLISH);
		assertThat(p.getDirection()).isEmpty();
	}
}
