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
import com.github.i49.pulp.api.vocabulary.IdentifierScheme;
import com.github.i49.pulp.api.vocabulary.dc.DublinCore;
import com.github.i49.pulp.api.vocabulary.dcterms.DublinCoreTerm;

/**
 * Unit tests for {@link PropertyFactory}.
 */
public class PropertyFactoryTest {
	
	private PropertyFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createPropertyFactory();
	}
	
	/* newFormat() */
	
	@Test
	public void newFormat_shouldCreateFormat() {
		String value = "application/epub+zip";
		SimpleProperty p = factory.newFormat(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.FORMAT);
		assertThat(p.getValue()).isEqualTo(value);
	}
	
	/* newSource(String) */
	
	@Test
	public void newSource_shouldCreateSource() {
		String value = "http://www.gutenberg.org/files/25545/25545-h/25545-h.htm";
		SourceProperty p = factory.newSource(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.SOURCE);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getScheme()).isEmpty();
	}
	
	/* newSubject(String) */
	
	@Test
	public void newSubject_shouldCreateSubject() {
		String value = "Olympic skiing";
		SubjectProperty p = factory.newSubject(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.SUBJECT);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getCode()).isEmpty();
	}

	/* newType(String) */
	
	@Test
	public void newType_shouldCreateType() {
		String value = "logbook";
		SimpleProperty p = factory.newType(value);
		assertThat(p.getTerm()).isSameAs(DublinCore.TYPE);
		assertThat(p.getValue()).isEqualTo(value);
	}
}
