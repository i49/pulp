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
	
	@Test
	public void getNumberOfProperties_shouldReturn4ByDefault() {
		assertThat(m.getNumberOfProperties()).isEqualTo(4);
		assertThat(m.getNumberOfProperties(BasicTerm.IDENTIFIER)).isEqualTo(1);
		assertThat(m.getNumberOfProperties(BasicTerm.TITLE)).isEqualTo(1);
		assertThat(m.getNumberOfProperties(BasicTerm.LANGUAGE)).isEqualTo(1);
		assertThat(m.getNumberOfProperties(BasicTerm.MODIFIED)).isEqualTo(1);
	}
}
