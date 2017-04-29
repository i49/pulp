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
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;

/**
 * Unit tests for {@link Metadata}.
 */
public class MetadataTest {

	private PropertyFactory f;
	private Metadata m;
	
	@Before
	public void setUp() {
		f = Epub.createPropertyFactory();
		Publication p = Epub.createPublication(); 
		Rendition r = p.addRendition();
		m = r.getMetadata();
	}
	
	/* getReleaseIdentifier() */
	
	@Test
	public void getReleaseIdentifier_shouldReturnValidReleaseIdentifier() {
		ReleaseIdentifier identifier = m.getReleaseIdentifier();
		assertThat(identifier).isNotNull();
	}
	
	/* getNumberOfProperties() */
	
	@Test
	public void getNumberOfProperties_shouldReturn0ByDefault() {
		assertThat(m.getNumberOfProperties()).isEqualTo(0);
	}
	
	@Test
	public void getNumberOfProperties_shouldReturn4IfMandatoryPropertiesAdded() {
		m.addMandatory();
		assertThat(m.getNumberOfProperties()).isEqualTo(4);
	}
	
	/* getNumberOfProperties(Term) */
	
	@Test
	public void getNumberOfProperties_shouldReturnCorrectNumber() {
		m.addMandatory();
		assertThat(m.getNumberOfProperties(BasicTerm.IDENTIFIER)).isEqualTo(1);
		assertThat(m.getNumberOfProperties(BasicTerm.TITLE)).isEqualTo(1);
		assertThat(m.getNumberOfProperties(BasicTerm.LANGUAGE)).isEqualTo(1);
		assertThat(m.getNumberOfProperties(BasicTerm.MODIFIED)).isEqualTo(1);
	}

	@Test
	public void getNumberOfProperties_shouldReturnZeroIfPropertyDoesNotExist() {
		assertThat(m.getNumberOfProperties(BasicTerm.CONTRIBUTOR)).isEqualTo(0);
		assertThat(m.getNumberOfProperties(BasicTerm.COVERAGE)).isEqualTo(0);
		assertThat(m.getNumberOfProperties(BasicTerm.CREATOR)).isEqualTo(0);
		assertThat(m.getNumberOfProperties(BasicTerm.DATE)).isEqualTo(0);
		assertThat(m.getNumberOfProperties(BasicTerm.SOURCE)).isEqualTo(0);
		assertThat(m.getNumberOfProperties(BasicTerm.SUBJECT)).isEqualTo(0);
	}
	
	/* contains(Term) */
	
	@Test
	public void contains_shouldReturnTrueIfPropertyExists() {
		m.add(f.newTitle("Untitled"));
		assertThat(m.contains(BasicTerm.TITLE)).isTrue();
	}

	@Test
	public void contains_shouldReturnFalseIfPropertyDoesNotExist() {
		assertThat(m.contains(BasicTerm.CREATOR)).isFalse();
	}
	
	/* getTerms() */
	
	@Test
	public void getTerms_shouldReturnNoTermsByDefault() {
		Set<Term> terms = m.getTerms();
		assertThat(terms).isEmpty();
	}

	@Test
	public void getTerms_shouldReturnAllTermsAdded() {
		Creator p = f.newCreator("John Smith");
		m.add(p);
		m.addMandatory();
		Set<Term> terms = m.getTerms();
		assertThat(terms).contains(
				BasicTerm.IDENTIFIER, BasicTerm.TITLE, BasicTerm.LANGUAGE, BasicTerm.MODIFIED, 
				BasicTerm.CREATOR);
	}
	
	/* get(Term) */
	
	@Test
	public void get_shouldReturnPropertyIfExists() {
		m.add(f.newTitle("Untitled"));
		Property p = m.get(BasicTerm.TITLE);
		assertThat(p).isNotNull();
		assertThat(p.getTerm()).isSameAs(BasicTerm.TITLE);
		assertThat(p.getValue()).isNotBlank();
	}
	
	@Test
	public void get_shouldThrowExceptionIfPropertyDoesNotExist() {
		Throwable thrown = catchThrowable(()->{
			m.get(BasicTerm.CREATOR);
		});
		assertThat(thrown).isInstanceOf(NoSuchElementException.class);
	}
	
	/* getList(Term) */
	
	@Test
	public void getList_shouldReturnNonEmptyListIfPropertyExists() {
		m.add(f.newTitle("Untitled"));
		List<Property> list = m.getList(BasicTerm.TITLE);
		assertThat(list).isNotNull();
		assertThat(list).hasSize(1);
		Property p = list.get(0);
		assertThat(p.getTerm()).isSameAs(BasicTerm.TITLE);
		assertThat(p.getValue()).isNotBlank();
	}
	
	@Test
	public void getList_shouldReturnEmptyListIfPropertyDoesNotExit() {
		List<Property> list = m.getList(BasicTerm.CREATOR);
		assertThat(list).isNotNull();
		assertThat(list).hasSize(0);
	}
	
	/* clear() */
	
	@Test
	public void clear_shouldClearAllProperties() {
		m.add(f.newIdentifier());
		m.add(f.newTitle("Untitled"));
		m.add(f.newLanguage(Locale.getDefault()));
		m.add(f.newModified(OffsetDateTime.now()));
		assertThat(m.getNumberOfProperties()).isEqualTo(4);
		m.clear();
		assertThat(m.getNumberOfProperties()).isEqualTo(0);
	}
	
	/* add(Property) */
	
	@Test
	public void add_shouldAddFirstProperty() {
		Creator p = f.newCreator("John Smith");
		assertThat(m.add(p)).isTrue();
		List<Property> list = m.getList(BasicTerm.CREATOR);
		assertThat(list).hasSize(1);
		assertThat(list).contains(p);
	}
	
	@Test
	public void add_shouldAddSecondProprety() {
		Title p = f.newTitle("The Catcher in the Rye");
		assertThat(m.add(p)).isTrue();
		List<Property> list = m.getList(BasicTerm.TITLE);
		assertThat(list).hasSize(1);
		assertThat(list).contains(p);
	}
	
	@Test
	public void add_shouldReturnFalseIfPropertyAlreadyExists() {
		Creator p = f.newCreator("John Smith");
		assertThat(m.add(p)).isTrue();
		assertThat(m.add(p)).isFalse();
		List<Property> list = m.getList(BasicTerm.CREATOR);
		assertThat(list).hasSize(1);
		assertThat(list).contains(p);
	}
	
	/* remove(Property) */

	@Test
	public void remove_shouldRemoveSpecifiedProperty() {
		Title p = f.newTitle("Untitled");
		m.add(p);
		assertThat(m.getNumberOfProperties()).isEqualTo(1);
		assertThat(m.contains(BasicTerm.TITLE)).isTrue();
		
		assertThat(m.remove(p)).isTrue();
		assertThat(m.getNumberOfProperties()).isEqualTo(0);
		assertThat(m.contains(BasicTerm.TITLE)).isFalse();
	}
	
	@Test
	public void remove_shouldReturnFalseIfPropertyDoesNotExist() {
		Creator p = f.newCreator("John Smith");
		assertThat(m.remove(p)).isFalse();
	}
	
	/** addMandatory() */
	
	@Test
	public void addMandatory_shouldAddRequiredProperties() {
		m.addMandatory();
		assertThat(m.getNumberOfProperties()).isEqualTo(4);
		
		Identifier identifier = (Identifier)m.get(BasicTerm.IDENTIFIER);
		assertThat(identifier).isNotNull();
		assertThat(identifier.getValue()).isNotBlank();
	
		Title title = (Title)m.get(BasicTerm.TITLE);
		assertThat(title).isNotNull();
		assertThat(title.getValue()).isNotBlank();

		Language language = (Language)m.get(BasicTerm.LANGUAGE);
		assertThat(language).isNotNull();
		assertThat(language.getLanguage()).isEqualTo(Locale.getDefault());

		Modified modified = (Modified)m.get(BasicTerm.MODIFIED);
		assertThat(modified).isNotNull();
	}
	
	@Test
	public void addMandatory_shouldNotAddPropertyAlreadPresent() {
		Title p = f.newTitle("The Catcher in the Rye");
		m.add(p);
		assertThat(m.getNumberOfProperties()).isEqualTo(1);
		m.addMandatory();
		assertThat(m.getNumberOfProperties()).isEqualTo(4);
		assertThat(m.getNumberOfProperties(BasicTerm.TITLE)).isEqualTo(1);
		assertThat(m.get(BasicTerm.TITLE)).isSameAs(p);
	}
}
