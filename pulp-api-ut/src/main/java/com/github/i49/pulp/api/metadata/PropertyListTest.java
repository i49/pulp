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

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.Rendition;

public class PropertyListTest {

	private PropertyFactory f;
	private Metadata m;
	
	@Before
	public void setUp() {
		f = Epub.createPropertyFactory();
		Publication p = Epub.createPublication(); 
		Rendition r = p.addRendition();
		m = r.getMetadata();
	}
	
	/* add(Property) */
	
	@Test
	public void add_shouldAddPropertyAtLast() {
		Creator p1 = f.newCreator("Lewis Carroll");
		Creator p2 = f.newCreator("John Tenniel");
		List<Property> list = m.getList(DublinCore.CREATOR);
		list.add(p1);
		list.add(p2);
		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0)).isSameAs(p1);
		assertThat(list.get(1)).isSameAs(p2);
	}
	
	@Test
	public void add_shouldThrowExceptionIfPropertyIsNull() {
		List<Property> list = m.getList(DublinCore.CREATOR);
		Throwable thrown = catchThrowable(()->{
			list.add(null);
		});
		assertThat(thrown).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void add_shouldThrowExceptionIfPropertyHasWrongTerm() {
		List<Property> list = m.getList(DublinCore.CREATOR);
		Contributor p = f.newContributor("John Smith");
		Throwable thrown = catchThrowable(()->{
			list.add(p);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void add_shouldThrowExceptionIfPropertyAlreadyExists() {
		Creator p = f.newCreator("Lewis Carroll");
		List<Property> list = m.getList(DublinCore.CREATOR);
		list.add(p);
		Throwable thrown = catchThrowable(()->{
			list.add(p);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void add_shouldThrowExceptionIfMultipleDatePropertiesAdded() {
		DateProperty p1 = f.newDate(OffsetDateTime.now());
		DateProperty p2 = f.newDate(OffsetDateTime.now());
		List<Property> list = m.getList(DublinCore.DATE);
		list.add(p1);
		Throwable thrown = catchThrowable(()->{
			list.add(p2);
		});
		assertThat(thrown).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void add_shouldThrowExceptionIfMultipleModifiedPropertiesAdded() {
		DateProperty p1 = f.newModified(OffsetDateTime.now());
		DateProperty p2 = f.newModified(OffsetDateTime.now());
		List<Property> list = m.getList(DublinCoreTerm.MODIFIED);
		list.add(p1);
		Throwable thrown = catchThrowable(()->{
			list.add(p2);
		});
		assertThat(thrown).isInstanceOf(IllegalStateException.class);
	}
	
	/* remove(int) */
	
	@Test
	public void remove_shouldRemovePropertyAtSpecifiedPosition() {
		List<Property> list = m.getList(DublinCore.TITLE);
		list.add(f.newTitle("Default Title"));
		Title p = f.newTitle("THE LORD OF THE RINGS"); 
		list.add(p);
		assertThat(list).hasSize(2);
		list.remove(0);
		assertThat(list).hasSize(1);
		assertThat(list.get(0)).isSameAs(p);
	}
	
	/* remove(Property) */
	
	@Test
	public void remove_shouldRemoveSpecifiedProperty() {
		List<Property> list = m.getList(DublinCore.TITLE);
		list.add(f.newTitle("Default Title"));
		Title p = f.newTitle("THE LORD OF THE RINGS"); 
		list.add(p);
		assertThat(list).hasSize(2);
		list.remove(list.get(0));
		assertThat(list).hasSize(1);
		assertThat(list.get(0)).isSameAs(p);
	}
	
	/* clear() */
	
	@Test
	public void clear_shouldClearAllPropertiesFromList() {
		List<Property> list = m.getList(DublinCore.CREATOR);
		list.add(f.newCreator("Lewis Carroll"));
		list.add(f.newCreator("John Tenniel"));
		assertThat(list).hasSize(2);
		list.clear();
		assertThat(list).isEmpty();
	}
}
