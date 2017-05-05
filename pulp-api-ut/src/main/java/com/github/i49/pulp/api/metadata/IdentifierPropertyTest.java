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
 * @author i49
 *
 */
public class IdentifierPropertyTest {

	private PropertyFactory f;
	private static final String UUID = "urn:uuid:6e8bc430-9c3a-11d9-9669-0800200c9a66";
	
	@Before
	public void setUp() {
		f = Epub.createPropertyFactory();
	}

	@Test
	public void getValue_shouldReturnIntialValue() {
		IdentifierProperty p = f.newIdentifier(UUID);
		assertThat(p.getValue()).isEqualTo(UUID);
	}
	
	@Test
	public void getScheme_shouldReturnSchemeAssinged() {
		IdentifierProperty p = f.newIdentifier(UUID).setScheme(IdentifierScheme.UUID);
		assertThat(p.getScheme()).hasValue(IdentifierScheme.UUID);
		assertThat(p.getSchemeURI()).isEmpty();
	}
}