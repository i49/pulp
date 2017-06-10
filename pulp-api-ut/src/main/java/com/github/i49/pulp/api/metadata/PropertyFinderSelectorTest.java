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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.publication.Epub;
import com.github.i49.pulp.api.publication.Publication;
import com.github.i49.pulp.api.publication.Rendition;
import com.github.i49.pulp.api.vocabularies.Property;
import com.github.i49.pulp.api.vocabularies.dc.Creator;
import com.github.i49.pulp.api.vocabularies.dc.DublinCore;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dc.Language;

public class PropertyFinderSelectorTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		Publication p = Epub.createPublication(); 
		Rendition r = p.addRendition();
		m = r.getMetadata();
	}

	/* all() */

	@Test
	public void all_shouldReturnEmptyByDefault() {
		assertThat(m.find().all()).isEmpty();
	}
	
	@Test
	public void all_shouldReturnAddedProperties() {
		m.add().identifier();
		m.add().creator("John Smith");
		m.add().language("en-US");
		Iterable<Property<?>> all = m.find().all();
		assertThat(all).hasSize(3);
		assertThat(all).hasOnlyElementsOfTypes(Identifier.class, Creator.class, Language.class);
	}
	
	/* creator() */
	
	@Test
	public void creator_shouldReturnAllCreators() {
		m.add().creator("Lewis Carroll");
		m.add().creator("John Tenniel");
		List<Creator> found = m.find().creator();
		assertThat(found).hasSize(2);
		assertThat(found.get(0).getValue()).isEqualTo("Lewis Carroll");
		assertThat(found.get(1).getValue()).isEqualTo("John Tenniel");
	}
	
	/* propertyOf(Term) */
	
	@Test
	public void propertyOf_shouldReturnSpecifiedProperties() {
		m.add().creator("Lewis Carroll");
		m.add().creator("John Tenniel");
		List<Property<?>> found = m.find().propertyOf(DublinCore.CREATOR);
		assertThat(found).hasSize(2);
		assertThat(found.get(0).getValue()).isEqualTo("Lewis Carroll");
		assertThat(found.get(1).getValue()).isEqualTo("John Tenniel");
	}
}
