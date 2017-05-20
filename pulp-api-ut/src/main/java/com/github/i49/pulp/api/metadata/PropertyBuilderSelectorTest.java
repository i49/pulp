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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.IllformedLocaleException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.vocabulary.Property;
import com.github.i49.pulp.api.vocabulary.dc.Contributor;
import com.github.i49.pulp.api.vocabulary.dc.Coverage;
import com.github.i49.pulp.api.vocabulary.dc.Creator;
import com.github.i49.pulp.api.vocabulary.dc.Date;
import com.github.i49.pulp.api.vocabulary.dc.Description;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dc.Format;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Language;
import com.github.i49.pulp.api.vocabulary.dc.Publisher;
import com.github.i49.pulp.api.vocabulary.dc.Relation;
import com.github.i49.pulp.api.vocabulary.dc.Rights;
import com.github.i49.pulp.api.vocabulary.dc.Source;
import com.github.i49.pulp.api.vocabulary.dc.Subject;
import com.github.i49.pulp.api.vocabulary.dc.Title;
import com.github.i49.pulp.api.vocabulary.dc.Type;
import com.github.i49.pulp.api.vocabulary.dcterms.DublinCoreTerm;
import com.github.i49.pulp.api.vocabulary.dcterms.Modified;

/**
 * Unit tests for {@link PropertyBuilderSelector}.
 */
public class PropertyBuilderSelectorTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		Publication p = Epub.createPublication();
		Rendition r = p.addRendition();
		m = r.getMetadata();
	}
	
	/* contributor(String) */

	@Test
	public void contributor_shouldBuildContributor() {
		String value = "John Smith";
		m.add().contributor(value);
		Contributor p = m.find().contributor().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.CONTRIBUTOR);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getRole()).isEmpty();
	}
	
	@Test
	public void contributor_shouldThrowExceptionIfValueIsNull() {
		Throwable thrown = catchThrowable(()->{
			m.add().contributor(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void contributor_shouldThrowExceptionIfValueIsBlank() {
		Throwable thrown = catchThrowable(()->{
			m.add().contributor(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	/* coverage(String) */
	
	@Test
	public void coverage_shouldBuildCoverage() {
		String value = "17th century";
		m.add().coverage(value);
		Coverage p = m.find().coverage().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.COVERAGE);
		assertThat(p.getValue()).isEqualTo(value);
	}

	/* creator(String) */

	@Test
	public void creator_shouldBuildCreator() {
		String value = "Lewis Carroll";
		m.add().creator(value);
		Creator p = m.find().creator().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.CREATOR);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getRole()).isEmpty();
	}
	
	@Test
	public void creator_shouldThrowExceptionIfValueIsNull() {
		Throwable thrown = catchThrowable(()->{
			m.add().creator(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void creator_shouldThrowExceptionIfValueIsBlank() {
		Throwable thrown = catchThrowable(()->{
			m.add().creator(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* date(OffsetDateTime) */
	
	@Test
	public void date_shouldBuildDate() {
		OffsetDateTime dateTime = OffsetDateTime.of(2017, 4, 23, 1, 2, 3, 0, ZoneOffset.ofHours(9));
		m.add().date(dateTime);
		Date p = m.find().date().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.DATE);
		assertThat(p.getValue()).isEqualTo(dateTime);
		assertThat(p.getValueAsString()).isEqualTo("2017-04-22T16:02:03Z");
	}

	@Test
	public void date_shouldThrowExceptionIfDateTimeIsNull() {
		Throwable thrown = catchThrowable(()->{
			m.add().date(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* description(String) */
	
	@Test
	public void description_shouldBuildDescription() {
		String value = "Illustrated guide to airport markings and lighting signals.";
		m.add().description(value);
		Description p = m.find().description().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.DESCRIPTION);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* format(String) */
	
	@Test
	public void format_shouldBuildFormat() {
		String value = "application/epub+zip";
		m.add().format(value);
		Format p = m.find().format().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.FORMAT);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* identifier() */

	@Test
	public void identifier_shouldGenerateIdentifier() {
		m.add().identifier();
		assertThat(m.find().identifier()).hasSize(1);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).startsWith("urn:uuid:");
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getSchemeURI()).isEmpty();
	}

	/* identifier(String) */
	
	@Test
	public void identifier_shouldBuildIdentifierWithExplicitValue() {
		String value = "urn:isbn:0451450523";
		m.add().identifier(value);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getSchemeURI()).isEmpty();
	}

	@Test
	public void identifier_shouldBuildIdentifierWithScheme() {
		String value = "urn:isbn:0451450523";
		m.add().identifier(value).scheme(Identifier.Scheme.ISBN);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).hasValue(Identifier.Scheme.ISBN);
		assertThat(p.getSchemeURI()).isEmpty();
	}
	
	@Test
	public void identifier_shouldThrowExceptionIfValueIsNull() {
		Throwable thrown = catchThrowable(()->{
			m.add().identifier(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void identifier_shouldThrowExceptionIfValueIsBlank() {
		Throwable thrown = catchThrowable(()->{
			m.add().identifier(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* language(Locale) */
	
	@Test
	public void language_shouldBuildLanguageWithLocale() {
		Locale language = Locale.forLanguageTag("en-US");
		m.add().language(language);
		Language p = m.find().language().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.LANGUAGE);
		assertThat(p.getValue()).isEqualTo(language);
		assertThat(p.getValueAsString()).isEqualTo("en-US");
	}

	@Test
	public void language_shouldBuildLanguageWithPredefinedLocale() {
		Locale language = Locale.FRENCH;
		m.add().language(language);
		Language p = m.find().language().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.LANGUAGE);
		assertThat(p.getValue()).isEqualTo(Locale.FRENCH);
		assertThat(p.getValueAsString()).isEqualTo("fr");
	}
	
	/* language(String) */

	@Test
	public void language_shouldBuildLanguageWithLangaugeTag() {
		String value = "en-US";
		m.add().language(value);
		Language p = m.find().language().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.LANGUAGE);
		assertThat(p.getValue()).isEqualTo(Locale.forLanguageTag(value));
		assertThat(p.getValueAsString()).isEqualTo("en-US");
	}

	@Test
	public void language_shouldThrowExceptionIfLanguageTagIsNull() {
		String value = null;
		Throwable thrown = catchThrowable(()->{
			m.add().language(value);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void language_shouldThrowExceptionIfLanguageTagIsBlank() {
		Throwable thrown = catchThrowable(()->{
			m.add().language(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void language_shouldThrowExceptionIfLanguageTagIsInvalid() {
		Throwable thrown = catchThrowable(()->{
			m.add().language("123");
		});
		assertThat(thrown).isInstanceOf(IllformedLocaleException.class);
	}

	/* modified(OffsetDateTime) */
	
	@Test
	public void modified_shouldBuildModified() {
		OffsetDateTime dateTime = OffsetDateTime.of(2017, 4, 23, 1, 2, 3, 0, ZoneOffset.ofHours(9));
		m.add().modified(dateTime);
		Modified p = m.find().modified().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCoreTerm.MODIFIED);
		assertThat(p.getValue()).isEqualTo(dateTime);
		assertThat(p.getValueAsString()).isEqualTo("2017-04-22T16:02:03Z");
	}

	@Test
	public void modified_shouldThrowExceptionIfDateTimeIsNull() {
		Throwable thrown = catchThrowable(()->{
			m.add().modified(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* publisher(String) */
	
	@Test
	public void publisher_shouldBuildPublisher() {
		String value = "O’Reilly Media, Inc.";
		m.add().publisher(value);
		Publisher p = m.find().publisher().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.PUBLISHER);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* relation(String) */
	
	@Test
	public void relation_shouldBuildRelation() {
		String value = "\"Two Lives\" [Resource is a collection of two novellas, one of which is \"Reading Turgenev\"]";
		m.add().relation(value);
		Relation p = m.find().relation().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.RELATION);
		assertThat(p.getValue()).isEqualTo(value);
	}

	/* rights(String) */

	@Test
	public void rights_shouldBuildRights() {
		String value = "Access limited to members";
		m.add().rights(value);
		Rights p = m.find().rights().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.RIGHTS);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* source(String) */

	@Test
	public void source_shouldBuildSource() {
		String value = "http://www.gutenberg.org/files/25545/25545-h/25545-h.htm";
		m.add().source(value);
		Source p = m.find().source().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.SOURCE);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).isEmpty();
	}

	/* subject(String) */

	@Test
	public void subject_shouldBuildSubject() {
		String value = "Olympic skiing";
		m.add().subject(value);
		Subject p = m.find().subject().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.SUBJECT);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getCode()).isEmpty();
	}
	
	/* title(String) */
	
	@Test
	public void title_shouldBuildTitle() {
		String value = "Norwegian Wood";
		m.add().title(value);
		Title p = m.find().title().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.TITLE);
		assertThat(p.getValue()).isEqualTo(value);
	}

	@Test
	public void title_shouldBuildTitleWithNormalizedValue() {
		String value = "THE LORD OF THE RINGS, Part One: The Fellowship of the Ring";
		String normalized = "Fellowship of the Ring";
		m.add().title(value).fileAs(normalized);
		Title p = m.find().title().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.TITLE);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getNormalizedValue()).hasValue(normalized);
	}

	@Test
	public void title_shouldThrowExceptionIfValueIsNull() {
		Throwable thrown = catchThrowable(()->{
			m.add().title(null);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void title_shouldThrowExceptionIfValueIsBlank() {
		Throwable thrown = catchThrowable(()->{
			m.add().title(" ");
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* type(String) */
	
	@Test
	public void type_shouldBuildType() {
		String value = "logbook";
		m.add().type(value);
		Type p = m.find().type().get(0);
		assertThat(p.getTerm()).isSameAs(DublinCore.TYPE);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* generic(Term, String) */
	
	@Test
	public void generic_shouldBuildGenericText() {
		String value = "elementary school pupils";
		m.add().generic(DublinCoreTerm.AUDIENCE, value);
		Property p = m.find().propertyOf(DublinCoreTerm.AUDIENCE).get(0); 
		assertThat(p.getTerm()).isSameAs(DublinCoreTerm.AUDIENCE);
		assertThat(p.getValue()).isEqualTo(value);
	}
}
