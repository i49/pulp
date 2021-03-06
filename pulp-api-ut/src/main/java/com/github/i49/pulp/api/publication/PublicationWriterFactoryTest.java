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

package com.github.i49.pulp.api.publication;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.publication.Epub;
import com.github.i49.pulp.api.publication.EpubException;
import com.github.i49.pulp.api.publication.PublicationWriter;
import com.github.i49.pulp.api.publication.PublicationWriterFactory;

/**
 * Unit tests for {@link PublicationWriterFactory}.
 */
public class PublicationWriterFactoryTest {

	private PublicationWriterFactory factory;

	@Before
	public void setUp() {
		factory = Epub.createWriterFactory();
	}
	
	/* createWriter */
	
	@Test
	public void createWriter_shouldCreateWriterWithPath() {
		Path path = Paths.get("target", "empty.epub");
		try (PublicationWriter writer = factory.createWriter(path)) {
			assertThat(writer).isNotNull();
		}
	}

	@Test
	public void createWriter_shouldThrowExceptionIfPathIsDirectory() {
		Path path = Paths.get("target");
		assertThatThrownBy(()->{
			factory.createWriter(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("target");
	}
}
