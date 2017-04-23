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

import com.github.i49.pulp.api.core.Epub;

public class PropertyListTest {

	private PropertyFactory factory;
	private Metadata m;
	
	@Before
	public void setUp() {
		factory = Epub.createPropertyFactory();
		m = Epub.createMetadata();
	}
	
	/* add(Property) */
	
	@Test
	public void add_shouldAddPropertyAtLast() {
		Creator p1 = factory.newCreator("Lewis Carroll");
		Creator p2 = factory.newCreator("John Tenniel");
		List<Property> list = m.getList(BasicTerm.CREATOR);
		list.add(p1);
		list.add(p2);
		assertThat(list.size()).isEqualTo(2);
		assertThat(list.get(0)).isSameAs(p1);
		assertThat(list.get(1)).isSameAs(p2);
	}
	
	@Test
	public void add_shouldThrowExceptionIfPropertyIsNull() {
		List<Property> list = m.getList(BasicTerm.CREATOR);
		Throwable thrown = catchThrowable(()->{
			list.add(null);
		});
		assertThat(thrown).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void add_shouldThrowExceptionIfPropertyHasWrongTerm() {
		List<Property> list = m.getList(BasicTerm.CREATOR);
		Contributor p = factory.newContributor("John Smith");
		Throwable thrown = catchThrowable(()->{
			list.add(p);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void add_shouldThrowExceptionIfPropertyAlreadyExists() {
		Creator p = factory.newCreator("Lewis Carroll");
		List<Property> list = m.getList(BasicTerm.CREATOR);
		list.add(p);
		Throwable thrown = catchThrowable(()->{
			list.add(p);
		});
		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
	
	/* remove(int) */
	
	@Test
	public void remove_shouldThrowExceptionIfPropertyAtSpecifiedIndexIsMandatory() {
		List<Property> list = m.getList(BasicTerm.TITLE);
		Throwable thrown = catchThrowable(()->{
			list.remove(0);
		});
		assertThat(thrown).isInstanceOf(IllegalStateException.class);
	}

	/* remove(Property) */
	
	@Test
	public void remove_shouldThrowExceptionIfPropertyIsMandatory() {
		List<Property> list = m.getList(BasicTerm.TITLE);
		Property p = list.get(0);
		Throwable thrown = catchThrowable(()->{
			list.remove(p);
		});
		assertThat(thrown).isInstanceOf(IllegalStateException.class);
	}
	
	/* clear() */
	
	@Test
	public void clear_shouldThrowExceptionIfPropertyIsMandatory() {
		List<Property> list = m.getList(BasicTerm.TITLE);
		Throwable thrown = catchThrowable(()->{
			list.clear();
		});
		assertThat(thrown).isInstanceOf(IllegalStateException.class);
	}
}
