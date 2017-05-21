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

package com.github.i49.pulp.api.vocabularies.dc;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.metadata.Metadata;

public class SubjectTest {

	private Metadata m;
	
	@Before
	public void setUp() {
		m = Epub.createPublication().addRendition().getMetadata();
	}
	
	/* getAuthority() */
	
	@Test
	public void getAuthority_shouldReturnAuthority() {
		String value = "FICTION / Occult & Supernatural";
		m.add().subject(value).ofCode(SubjectAuthority.BISAC, "FIC024000");
		Subject p = m.find().subject().get(0);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).hasValue(SubjectAuthority.BISAC);
		assertThat(p.getScheme()).isEmpty();
		assertThat(p.getCode()).hasValue("FIC024000");
	}

	/* getScheme() */

	@Test
	public void getScheme_shouldReturnScheme() {
		String value = "Number Theory";
		URI scheme = URI.create("http://www.ams.org/msc/msc2010.html");
		m.add().subject(value).ofCode(scheme, "11");
		Subject p = m.find().subject().get(0);
		assertThat(p.getValue()).isEqualTo(value);
		assertThat(p.getAuthority()).isEmpty();
		assertThat(p.getScheme()).hasValue(scheme);
		assertThat(p.getCode()).hasValue("11");
	}

	/* getValue() */

	@Test
	public void getValue_shouldReturnValueOfProoerty() {
		String value = "Olympic skiing";
		m.add().subject(value);
		Subject p = m.find().subject().get(0);
		assertThat(p.getValue()).isEqualTo(value);
	}
}
