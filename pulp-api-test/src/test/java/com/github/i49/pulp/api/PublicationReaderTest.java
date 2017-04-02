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

package com.github.i49.pulp.api;

import static com.github.i49.pulp.api.EpubAssertions.*;
import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link PublicationReader}.
 */
public class PublicationReaderTest {

	private static final Path BASE_PATH = Paths.get("target", "epub");

	private PublicationReaderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createReaderFactory();
	}
	
	private Path pathTo(String path) {
		return BASE_PATH.resolve(path);
	}
	
	/* read() */
	
	@Test
	public void read_shouldThrowExceptionIfContainerXmlIsMissing() {
		Path path = pathTo("container-missing.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("META-INF/container.xml")
			.hasContainerPath(path);
	}
	
	@Test
	public void read_shouldThrowExceptionIfContainerXmlIsEmpty() {
		Path path = pathTo("container-empty.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("META-INF/container.xml")
			.hasContainerPath(path);
	}
	
	@Test
	public void read_shouldThrowExceptionIfContainerXmlHasWrongRootElement() {
		Path path = pathTo("container-wrong-root.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("META-INF/container.xml")
			.hasContainerPath(path);
	}

	@Test
	public void read_shouldThrowExceptionIfContainerXmlHasUnsupportedVersion() {
		Path path = pathTo("container-unsupported.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("META-INF/container.xml")
			.hasContainerPath(path);
	}
}
