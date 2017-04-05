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

import java.net.URI;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link PublicationResourceBuilderFactory}.
 */
public class PublicationResourceBuilderFactoryTest {

	private static final URI BASE_URI = URI.create("EPUB/package.opf");
	
	private PublicationResourceBuilderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createResourceBuilderFactory(BASE_URI);
	}

	/* newBuilder */
	
	@Test
	public void newBuilder_shouldCreateLocalResourceBuilder() {
		PublicationResourceBuilder builder = factory.newBuilder("images/cover.png");
		assertThat(builder).isNotNull();
	}

	@Test
	public void newBuilder_shouldCreateRelativeResourceBuilder() {
		PublicationResourceBuilder builder = factory.newBuilder("../other/images/cover.png");
		assertThat(builder).isNotNull();
	}

	@Test
	public void newBuilder_shouldThrowExceptionIfLocalLocationIsInvalid() {
		assertThatThrownBy(()->{
			factory.newBuilder("../../images/cover.png");
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void newBuilder_shouldCreateRemoteResourceBuilder() {
		PublicationResourceBuilder builder = factory.newBuilder("http://example.org/images/cover.png");
		assertThat(builder).isNotNull();
	}

	@Test
	public void newBuilder_shouldThrowExceptionIfRemoteLocationIsOpaque() {
		assertThatThrownBy(()->{
			factory.newBuilder("http:./images/cover.png");
		}).isInstanceOf(IllegalArgumentException.class);
	}
}
