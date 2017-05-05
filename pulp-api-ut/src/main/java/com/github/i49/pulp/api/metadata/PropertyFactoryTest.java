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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.IllformedLocaleException;
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
		factory = Epub.createPropertyFactory();
	}
	
	/* newContributor(String) */
	
	@Test
	public void newContributor_shouldCreateContributor() {
		String name = "John Smith";
		Contributor p = factory.newContributor(name);
		assertThat(p.getTerm()).isSameAs(DublinCore.CONTRIBUTOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEmpty();
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}

	@Test 
	public void newContributor_shouldThrowExceptionIfNameIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newContributor(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test 
	public void newContributor_shouldThrowExceptionIfNameIsBlank() {
		Throwable thrown = catchThrowable(()->{
			factory.newContributor(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* newContributor(String, Locale) */

	@Test
	public void newContributor_shouldCreateContributorWithLanguage() {
		String name = "Hans Schmidt";
		Contributor p = factory.newContributor(name, Locale.GERMAN);
		assertThat(p.getTerm()).isSameAs(DublinCore.CONTRIBUTOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEmpty();
		assertThat(p.getLanguage()).hasValue(Locale.GERMAN);
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}
	
	/* newCoverage(String) */
	
	@Test
	public void newCoverage_shouldCreateCoverage() {
		String text = "17th century";
		TextProperty p = factory.newCoverage(text);
		assertThat(p.getTerm()).isSameAs(DublinCore.COVERAGE);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newCreator(String) */
	
	@Test
	public void newCreator_shouldCreateCreator() {
		String name = "John Smith";
		Creator p = factory.newCreator(name);
		assertThat(p.getTerm()).isSameAs(DublinCore.CREATOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEmpty();
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}

	@Test 
	public void newCreator_shouldThrowExceptionIfNameIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newCreator(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test 
	public void newCreator_shouldThrowExceptionIfNameIsBlank() {
		Throwable thrown = catchThrowable(()->{
			factory.newCreator(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* newCreator(String, Locale) */

	@Test
	public void newCreator_shouldCreateCreatorWithLanguage() {
		String name = "Hans Schmidt";
		Creator p = factory.newCreator(name, Locale.GERMAN);
		assertThat(p.getTerm()).isSameAs(DublinCore.CREATOR);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEmpty();
		assertThat(p.getLanguage()).hasValue(Locale.GERMAN);
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).isEmpty();
	}
	
	/* newDate(OffsetDateTime) */
	
	@Test
	public void newDate_shouldCreateDateOfSpecifiedDateTime() {
		OffsetDateTime dateTime = OffsetDateTime.of(2017, 4, 23, 1, 2, 3, 0, ZoneOffset.ofHours(9));
		DateProperty p = factory.newDate(dateTime);
		assertThat(p.getTerm()).isSameAs(DublinCore.DATE);
		assertThat(p.getValue()).isEqualTo(dateTime);
		assertThat(p.getValueAsString()).isEqualTo("2017-04-22T16:02:03Z");
	}

	@Test 
	public void newDate_shouldThrowExceptionIfDateTimeIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newDate(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* newDescription(String) */
	
	@Test
	public void newDescription_shouldCreateDescription() {
		String text = "Illustrated guide to airport markings and lighting signals.";
		TextProperty p = factory.newDescription(text);
		assertThat(p.getTerm()).isSameAs(DublinCore.DESCRIPTION);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}
	
	@Test 
	public void newDescription_shouldThrowExceptionIfTextIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newDescription(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test 
	public void newDescription_shouldThrowExceptionIfTextIsBlank() {
		Throwable thrown = catchThrowable(()->{
			factory.newDescription("   ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* newFormat() */
	
	@Test
	public void newFormat_shouldCreateFormat() {
		String value = "application/epub+zip";
		SimpleProperty p = factory.newFormat(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.FORMAT);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* newIdentifier() */
	
	@Test
	public void newIdentifier_shouldCreateRandomIdentifier() {
		IdentifierProperty identifier = factory.newIdentifier();
		assertThat(identifier.getTerm()).isSameAs(DublinCore.IDENTIFIER);
		assertThat(identifier.getValueAsString()).startsWith("urn:uuid:");
		assertThat(identifier.getScheme()).hasValue(IdentifierScheme.UUID);
		assertThat(identifier.getSchemeURI()).isEmpty();
	}

	/* newIdentifier(String) */
	
	@Test
	public void newIdentifier_shouldCreateIdentifierWithValue() {
		String value = "urn:isbn:0451450523";
		IdentifierProperty identifier = factory.newIdentifier(value);
		assertThat(identifier.getTerm()).isSameAs(DublinCore.IDENTIFIER);
		assertThat(identifier.getValue()).isEqualTo(value);
		assertThat(identifier.getScheme()).isEmpty();
		assertThat(identifier.getSchemeURI()).isEmpty();
	}
	
	@Test
	public void newIdentifier_shouldThrowExceptionIfValueIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newIdentifier(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void newIdentifier_shouldThrowExceptionIfValueIsBlank() {
		Throwable thrown = catchThrowable(()->{
			factory.newIdentifier(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException .class);
	}

	/* newLanguage(String) */

	@Test
	public void newLanguage_shouldCreateLanguageByLanguageTag() {
		LanguageProperty p = factory.newLanguage("en-US");
		assertThat(p.getTerm()).isSameAs(DublinCore.LANGUAGE);
		assertThat(p.getValue()).isEqualTo(Locale.forLanguageTag("en-US"));
		assertThat(p.getValueAsString()).isEqualTo("en-US");
	}
	
	@Test
	public void newLanguage_shouldThrowExceptionIfLangaugeTagIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newLanguage((String)null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException .class);
	}

	@Test
	public void newLanguage_shouldThrowExceptionIfLangaugeTagIsBlank() {
		Throwable thrown = catchThrowable(()->{
			factory.newLanguage("");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException .class);
	}

	@Test
	public void newLanguage_shouldThrowExceptionIfLangaugeTagIsInvalid() {
		Throwable thrown = catchThrowable(()->{
			factory.newLanguage("123");
		});
		assertThat(thrown).isInstanceOf(IllformedLocaleException .class);
	}
	
	/* newLanguage(Locale) */
	
	@Test
	public void newLanguage_shouldCreateLanguageByLocale() {
		Locale language = Locale.forLanguageTag("en-US");
		LanguageProperty p = factory.newLanguage(language);
		assertThat(p.getTerm()).isSameAs(DublinCore.LANGUAGE);
		assertThat(p.getValue()).isSameAs(language);
		assertThat(p.getValueAsString()).isEqualTo("en-US");
	}

	@Test
	public void newLanguage_shouldCreateLanguageByPredefinedLocale() {
		Locale language = Locale.FRENCH;
		LanguageProperty p = factory.newLanguage(language);
		assertThat(p.getTerm()).isSameAs(DublinCore.LANGUAGE);
		assertThat(p.getValue()).isSameAs(language);
		assertThat(p.getValueAsString()).isEqualTo("fr");
	}
	
	/* newModified(OffsetDateTime) */
	
	@Test
	public void newModified_shouldCreateModifiedOfSpecifiedDateTime() {
		OffsetDateTime dateTime = OffsetDateTime.of(2017, 4, 23, 1, 2, 3, 0, ZoneOffset.ofHours(9));
		DateProperty p = factory.newModified(dateTime);
		assertThat(p.getTerm()).isSameAs(DublinCoreTerm.MODIFIED);
		assertThat(p.getValue()).isEqualTo(dateTime);
		assertThat(p.getValueAsString()).isEqualTo("2017-04-22T16:02:03Z");
	}

	@Test 
	public void newModified_shouldThrowExceptionIfDateTimeIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newModified(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* newPublisher(String) */
	
	@Test
	public void newPublisher_shouldCreatePublisher() {
		String name = "O’Reilly Media, Inc.";
		Publisher p = factory.newPublisher(name);
		assertThat(p.getTerm()).isSameAs(DublinCore.PUBLISHER);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEmpty();
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).hasValue("pbl");
	}

	@Test 
	public void newPublisher_shouldThrowExceptionIfNameIsNull() {
		Throwable thrown = catchThrowable(()->{
			factory.newPublisher(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test 
	public void newPublisher_shouldThrowExceptionIfNameIsBlank() {
		Throwable thrown = catchThrowable(()->{
			factory.newPublisher(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* newPublisher(String, Locale) */

	@Test
	public void newPublisher_shouldCreatePublisherWithLanguage() {
		String name = "Lagardère Publishing";
		Publisher p = factory.newPublisher(name, Locale.FRENCH);
		assertThat(p.getTerm()).isSameAs(DublinCore.PUBLISHER);
		assertThat(p.getValue()).isEqualTo(name);
		assertThat(p.getNormalizedValue()).isEmpty();
		assertThat(p.getLanguage()).hasValue(Locale.FRENCH);
		assertThat(p.getDirection()).isEmpty();
		assertThat(p.getAlternativeRepresentation()).isEmpty();
		assertThat(p.getRole()).hasValue("pbl");
	}
	
	/* newRelation(String) */
	
	@Test
	public void newRelation_shouldCreateRelation() {
		String text = "\"Two Lives\" [Resource is a collection of two novellas, one of which is \"Reading Turgenev\"]";
		TextProperty p = factory.newRelation(text);
		assertThat(p.getTerm()).isSameAs(DublinCore.RELATION);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newRighs(String) */
	
	@Test
	public void newRights_shouldCreateRights() {
		String text = "Access limited to members";
		TextProperty p = factory.newRights(text);
		assertThat(p.getTerm()).isSameAs(DublinCore.RIGHTS);
		assertThat(p.getValue()).isEqualTo(text);
		assertThat(p.getLanguage()).isEmpty();
		assertThat(p.getDirection()).isEmpty();
	}

	/* newSource(String) */
	
	@Test
	public void newSource_shouldCreateSource() {
		String value = "http://www.gutenberg.org/files/25545/25545-h/25545-h.htm";
		Source p = factory.newSource(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.SOURCE);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).isEmpty();
	}
	
	/* newSubject(String) */
	
	@Test
	public void newSubject_shouldCreateSubject() {
		String value = "Olympic skiing";
		Subject p = factory.newSubject(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.SUBJECT);
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
		assertThat(p.getTerm()).isSameAs(DublinCore.SUBJECT);
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
		assertThat(p.getTerm()).isSameAs(DublinCore.SUBJECT);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).hasValue(scheme);
		assertThat(p.getCode()).hasValue("11");
	}
	
	/* newType(String) */
	
	@Test
	public void newType_shouldCreateType() {
		String value = "logbook";
		SimpleProperty p = factory.newType(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.TYPE);
		assertThat(p.getValue()).isEqualTo(value);
	}

	@Test
	public void newType_shouldCreatePredefinedType() {
		String value = "dictionary";
		SimpleProperty p = factory.newType(value);
		assertThat(p).isSameAs(PublicationType.DICTIONARY);
		assertThat(p.getTerm()).isSameAs(DublinCore.TYPE);
		assertThat(p.getValue()).isEqualTo(value);
	}
}
