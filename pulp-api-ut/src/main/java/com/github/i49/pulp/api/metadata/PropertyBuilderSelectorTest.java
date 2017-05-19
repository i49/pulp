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

import java.util.IllformedLocaleException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dc.Identifier;
import com.github.i49.pulp.api.vocabulary.dc.Language;

/**
 *
 */
public class PropertyBuilderSelectorTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		Publication p = Epub.createPublication();
		Rendition r = p.addRendition();
		m = r.getMetadata();
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
	public void identifier_shouldBuildIdentifierWithSpecifiedValue() {
		String value = "urn:isbn:0451450523";
		m.add().identifier(value);
		Identifier p = m.find().identifier().get(0);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getSchemeURI()).isEmpty();
	}

	@Test
	public void identifier_shouldBuildIdentifierWithSpecifiedScheme() {
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

	/* title(String) */
	
	
}
