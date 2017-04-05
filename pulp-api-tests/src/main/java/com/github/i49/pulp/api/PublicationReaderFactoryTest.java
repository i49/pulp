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

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link PublicationReaderFactory}.
 */
public class PublicationReaderFactoryTest {
	
	private static final Path BASE_PATH = Paths.get("target", "epub");

	private PublicationReaderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createReaderFactory();
	}
	
	/* createReader() */
	
	@Test
	public void createReader_shoudThrowExceptionIfPathNotExist() {
		Path path = Paths.get("nonexistent.epub");
		assertThatThrownBy(()->{
			factory.createReader(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("nonexistent.epub");
	}
	
	@Test
	public void createReader_shouldThrowExceptionIfPathIsNull() {
		assertThatThrownBy(()->{
			factory.createReader(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void createReader_shouldThrowExceptionIfFileIsEmpty() {
		Path path = BASE_PATH.resolve("empty.epub");
		assertThatThrownBy(()->{
			factory.createReader(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("empty.epub");
	}
	
	@Test
	public void createReader_shouldThrowExceptionIfMimetypeIsMissing() {
		Path path = BASE_PATH.resolve("mimetype-missing.epub");
		assertThatThrownBy(()->{
			factory.createReader(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("mimetype-missing.epub");
	}
	
	@Test
	public void createReader_shouldThrowExceptionIfMimetypeIsWrong() {
		Path path = BASE_PATH.resolve("mimetype-wrong.epub");
		assertThatThrownBy(()->{
			factory.createReader(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("mimetype-wrong.epub");
	}

	@Test
	public void createReader_shouldThrowExceptionIfMimetypeIsDirectory() {
		Path path = BASE_PATH.resolve("mimetype-directory.epub");
		assertThatThrownBy(()->{
			factory.createReader(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("mimetype-directory.epub");
	}
}
