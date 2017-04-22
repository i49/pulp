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

import java.net.URI;
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
	
	/* newFormat() */
	
	@Test
	public void newFormat_shouldCreateFormat() {
		String value = "application/epub+zip";
		Format p = factory.newFormat(value);
		assertThat(p.getTerm()).isSameAs(BasicTerm.FORMAT);
		assertThat(p.getValue()).isEqualTo(value);
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
	
	/* newLanguage(String) */

	@Test
	public void newLanguage_shouldCreateLanguageByLanguageTag() {
		Language p = factory.newLanguage("en-US");
		assertThat(p.getTerm()).isSameAs(BasicTerm.LANGUAGE);
		assertThat(p.getValue()).isEqualTo("en-US");
		assertThat(p.getLanguage()).isEqualTo(Locale.forLanguageTag("en-US"));
	}
	
	/* newLanguage(Locale) */
	
	@Test
	public void newLanguage_shouldCreateLanguageByLocale() {
		Locale language = Locale.forLanguageTag("en-US");
		Language p = factory.newLanguage(language);
		assertThat(p.getTerm()).isSameAs(BasicTerm.LANGUAGE);
		assertThat(p.getValue()).isEqualTo("en-US");
		assertThat(p.getLanguage()).isSameAs(language);
	}

	@Test
	public void newLanguage_shouldCreateLanguageByPredefinedLocale() {
		Locale language = Locale.FRENCH;
		Language p = factory.newLanguage(language);
		assertThat(p.getTerm()).isSameAs(BasicTerm.LANGUAGE);
		assertThat(p.getValue()).isEqualTo("fr");
		assertThat(p.getLanguage()).isSameAs(language);
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
	
	/* newSource(String) */
	
	@Test
	public void newSource_shouldCreateSource() {
		String value = "http://www.gutenberg.org/files/25545/25545-h/25545-h.htm";
		Source p = factory.newSource(value);
		assertThat(p.getTerm()).isSameAs(BasicTerm.SOURCE);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).isEmpty();
	}
	
	/* newSubject(String) */
	
	@Test
	public void newSubject_shouldCreateSubject() {
		String value = "Olympic skiing";
		Subject p = factory.newSubject(value);
		assertThat(p.getTerm()).isSameAs(BasicTerm.SUBJECT);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getCode()).isEmpty();
	}

	/* newSubject(String, SubjectAuthority) */
	
	@Test
	public void newSubject_shouldCreateSubjectWithAuthority() {
		String value = "FICTION / Occult & Supernatural";
		Subject p = factory.newSubject(value, SubjectAuthority.BISAC, "FIC024000");
		assertThat(p.getTerm()).isSameAs(BasicTerm.SUBJECT);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).hasValue(SubjectAuthority.BISAC);
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getCode()).hasValue("FIC024000");
	}

	/* newSubject(String, URI) */
	
	@Test
	public void newSubject_shouldCreateSubjectWithSchemeURI() {
		String value = "Number Theory";
		URI scheme = URI.create("http://www.ams.org/msc/msc2010.html");
		Subject p = factory.newSubject(value, scheme, "11");
		assertThat(p.getTerm()).isSameAs(BasicTerm.SUBJECT);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).hasValue(scheme);
		assertThat(p.getCode()).hasValue("11");
	}
	
	/* newType(String) */
	
	@Test
	public void newType_shouldCreateType() {
		String value = "logbook";
		Type p = factory.newType(value);
		assertThat(p.getTerm()).isSameAs(BasicTerm.TYPE);
		assertThat(p.getValue()).isEqualTo(value);
	}

	@Test
	public void newType_shouldCreatePredefinedType() {
		String value = "dictionary";
		Type p = factory.newType(value);
		assertThat(p).isSameAs(PublicationType.DICTIONARY);
		assertThat(p.getTerm()).isSameAs(BasicTerm.TYPE);
		assertThat(p.getValue()).isEqualTo(value);
	}
}
