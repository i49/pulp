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
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.vocabularies.Term;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;
import com.github.i49.pulp.api.vocabularies.dc.Title;
import com.github.i49.pulp.api.vocabularies.dcterms.DublinCoreTerm;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;

/**
 * Unit tests for {@link Metadata}.
 */
public class MetadataTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		m = Epub.createPublication().addRendition().getMetadata();
	}
	
	/* add() */
	
	@Test
	public void add_shouldAddCreator() {
		m.add().creator("John Smith");
		List<Creator> list = m.find().creator();
		assertThat(list).hasSize(1);
	}
	
	@Test
	public void add_shouldAddTitle() {
		m.add().title("The Catcher in the Rye");
		List<Title> list = m.find().title();
		assertThat(list).hasSize(1);
	}
	
	/* clear() */
	
	@Test
	public void clear_shouldClearAllProperties() {
		m.add().identifier();
		m.add().title("Untitled");
		m.add().language(Locale.getDefault());
		m.add().modified(OffsetDateTime.now());
		assertThat(m.size()).isEqualTo(4);
		m.clear();
		assertThat(m.size()).isEqualTo(0);
	}
	
	/* contains() */
	
	@Test
	public void contains_shouldReturnTrueIfPropertyExists() {
		m.add().title("Untitled");
		assertThat(m.contains().title()).isTrue();
	}

	@Test
	public void contains_shouldReturnFalseIfPropertyDoesNotExist() {
		assertThat(m.contains().creator()).isFalse();
	}

	/* count() */
	
	@Test
	public void counter_shouldReturnCorrectNumber() {
		m.fillMissingProperties();
		assertThat(m.count().identifier()).isEqualTo(1);
		assertThat(m.count().title()).isEqualTo(1);
		assertThat(m.count().language()).isEqualTo(1);
		assertThat(m.count().modified()).isEqualTo(1);
	}

	@Test
	public void getNumberOfProperties_shouldReturnZeroIfPropertyDoesNotExist() {
		assertThat(m.count().contributor()).isEqualTo(0);
		assertThat(m.count().coverage()).isEqualTo(0);
		assertThat(m.count().creator()).isEqualTo(0);
		assertThat(m.count().date()).isEqualTo(0);
		assertThat(m.count().source()).isEqualTo(0);
		assertThat(m.count().subject()).isEqualTo(0);
	}
	
	/* fillMissingProperties() */
	
	@Test
	public void fillMissingProperties_shouldAddRequiredProperties() {
		m.fillMissingProperties();
		assertThat(m.size()).isEqualTo(4);
		
		Identifier identifier = m.find().identifier().get(0);
		assertThat(identifier).isNotNull();
		assertThat(identifier.getValueAsString()).isNotBlank();
	
		Title title = m.find().title().get(0);
		assertThat(title).isNotNull();
		assertThat(title.getValueAsString()).isNotBlank();

		Language language = m.find().language().get(0);
		assertThat(language).isNotNull();
		assertThat(language.getValue()).isEqualTo(Locale.getDefault());

		Modified modified = m.find().modified().get(0);
		assertThat(modified).isNotNull();
	}
	
	@Test
	public void fillMissingProperties_shouldNotAddPropertyAlreadyPresent() {
		Title p = m.add().title("The Catcher in the Rye").result();
		
		assertThat(m.size()).isEqualTo(1);
		m.fillMissingProperties();
		assertThat(m.size()).isEqualTo(4);
		assertThat(m.count().title()).isEqualTo(1);
		assertThat(m.find().title().get(0)).isSameAs(p);
	}
	
	/* find() */
	
	@Test
	public void find_shouldReturnSpecificProperty() {
		m.add().title("Untitled");
		Title p = m.find().title().get(0);
		assertThat(p).isNotNull();
		assertThat(p.getTerm()).isSameAs(DublinCore.TITLE);
		assertThat(p.getValueAsString()).isNotBlank();
	}
	
	@Test
	public void find_shouldReturnEmptyByDefault() {
		assertThat(m.find().all()).isEmpty();
	}
	
	@Test
	public void find_shouldReturnAllProperties() {
		Identifier p1 = m.add().identifier().result();
		Title p2 = m.add().title("title").result();
		Title p3 = m.add().title("subtitle").result();
		Modified p4 = m.add().modified(OffsetDateTime.now()).result();
		assertThat(m.find().all()).hasSize(4).contains(p1, p2, p3, p4);
	}
	
	/* getReleaseIdentifier() */
	
	@Test
	public void getReleaseIdentifier_shouldReturnValidReleaseIdentifier() {
		ReleaseIdentifier identifier = m.getReleaseIdentifier();
		assertThat(identifier).isNotNull();
	}
	
	/* isFilled */
	
	@Test
	public void isFilled_shouldReturnFalseByDefault() {
		assertThat(m.isFilled()).isFalse();
	}
	
	@Test
	public void isFilled_shouldReturnTrueIfAllRequiredPropertiesExist() {
		m.add().identifier();
		m.add().title("Untitled");
		m.add().language(Locale.getDefault());
		m.add().modified(OffsetDateTime.now());
		assertThat(m.isFilled()).isTrue();
	}
	
	/* remove(Property) */

	@Test
	public void remove_shouldRemoveSpecifiedProperty() {
		m.add().title("Untitled");
		Title p = m.find().title().get(0);
		
		assertThat(m.size()).isEqualTo(1);
		assertThat(m.contains().title()).isTrue();
		
		assertThat(m.remove(p)).isTrue();
		assertThat(m.size()).isEqualTo(0);
		assertThat(m.contains().title()).isFalse();
	}
	
	@Test
	public void remove_shouldReturnFalseIfPropertyDoesNotExist() {
		m.add().creator("John Smith");
		Creator p = m.find().creator().get(0);
		assertThat(m.remove(p)).isTrue();
		assertThat(m.remove(p)).isFalse();
	}

	/* size() */
	
	@Test
	public void size_shouldReturn0ByDefault() {
		assertThat(m.size()).isEqualTo(0);
	}
	
	@Test
	public void size_shouldReturn4IfMandatoryPropertiesAdded() {
		m.fillMissingProperties();
		assertThat(m.size()).isEqualTo(4);
	}

	/* termSet() */
	
	@Test
	public void getTerms_shouldReturnNoTermsByDefault() {
		Set<Term> terms = m.termSet();
		assertThat(terms).isEmpty();
	}

	@Test
	public void getTerms_shouldReturnAllTermsAdded() {
		m.add().creator("John Smith");
		m.fillMissingProperties();
		Set<Term> terms = m.termSet();
		assertThat(terms).contains(
				DublinCore.IDENTIFIER, DublinCore.TITLE, DublinCore.LANGUAGE, DublinCoreTerm.MODIFIED, 
				DublinCore.CREATOR);
	}
}
