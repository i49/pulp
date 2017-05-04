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
import static com.github.i49.pulp.api.metadata.DublinCore.*;

import org.junit.Test;

/**
 * Unit tests for {@link DublinCore}.
 */
public class DublinCoreTest {
	
	/* isRepeatable() */
	
	@Test
	public void isRepeatable_shouldReturnFalseForDate() {
		assertThat(DATE.isRepeatable()).isFalse();
	}

	@Test
	public void isRepeatable_shouldReturnTrueExceptDate() {
		assertThat(IDENTIFIER.isRepeatable()).isTrue();
		assertThat(TITLE.isRepeatable()).isTrue();
		assertThat(LANGUAGE.isRepeatable()).isTrue();
	}

	/* localName() */

	@Test
	public void localName_shouldReturnLocalName() {
		assertThat(TITLE.localName()).isEqualTo("title");
	}

	/* qualifiedName() */

	@Test
	public void qualifiedName_shouldReturnQualifiedName() {
		assertThat(TITLE.qualifiedName()).isEqualTo("http://purl.org/dc/elements/1.1/title");
	}

	/* vocabulary() */

	@Test
	public void vocabulary_shouldReturnDCMES() {
		assertThat(TITLE.vocabulary()).isSameAs(StandardVocabulary.DCMES);
	}
}
