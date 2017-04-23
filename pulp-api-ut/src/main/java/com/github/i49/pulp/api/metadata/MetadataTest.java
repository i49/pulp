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
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;

/**
 * Unit tests for {@link Metadata}.
 */
public class MetadataTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		m = Epub.createMetadata();
	}
	
	/* getNumberOfProperties() */
	
	@Test
	public void getNumberOfProperties_shouldReturn4ByDefault() {
		assertThat(m.getNumberOfProperties()).isEqualTo(4);
	}
	
	/* getNumberOfProperties(Term) */
	
	@Test
	public void getNumberOfProperties_shouldReturnCorrectNumber() {
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
		assertThat(m.contains(BasicTerm.TITLE)).isTrue();
	}

	@Test
	public void contains_shouldReturnFalseIfPropertyDoesNotExist() {
		assertThat(m.contains(BasicTerm.CREATOR)).isFalse();
	}
	
	/* get(Term) */
	
	@Test
	public void get_shouldReturnPropertyIfExists() {
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
}
