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

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.vocabulary.dc.SubjectAuthority;

/**
 * Unit tests for {@link SubjectProperty}.
 */
public class SubjectPropertyTest {

	private PropertyFactory f;

	@Before
	public void setUp() {
		f = Epub.createPropertyFactory();
	}

	@Test
	public void getValue_shouldReturnInitialValue() {
		String value = "Olympic skiing";
		SubjectProperty p = f.newSubject(value);
		assertThat(p.getValue()).isEqualTo(value);
	}

	@Test
	public void setCode_shouldAssignAuthorityAndCode() {
		String value = "FICTION / Occult & Supernatural";
		SubjectProperty p = f.newSubject(value).setCode(SubjectAuthority.BISAC, "FIC024000");
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).hasValue(SubjectAuthority.BISAC);
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getCode()).hasValue("FIC024000");
	}

	@Test
	public void setCode_shouldAssignSchemeAndCode() {
		String value = "Number Theory";
		URI scheme = URI.create("http://www.ams.org/msc/msc2010.html");
		SubjectProperty p = f.newSubject(value).setCode(scheme, "11");
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).hasValue(scheme);
		assertThat(p.getCode()).hasValue("11");
	}
	
	@Test
	public void setValue_shouldModifyValue() {
		String oldValue = "Olympic skiing";
		SubjectProperty p = f.newSubject(oldValue);
		assertThat(p.getValue()).isEqualTo(oldValue);
		String newValue = "FICTION / Occult & Supernatural";
		p.setValue(newValue);
		assertThat(p.getValue()).isEqualTo(newValue);
	}
}
