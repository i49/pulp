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

package com.github.i49.pulp.api.core;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.Test;

import com.github.i49.pulp.api.core.CoreMediaType;
import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.MediaType;
import com.github.i49.pulp.api.core.PublicationResource;
import com.github.i49.pulp.api.core.PublicationResourceBuilder;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;

/**
 * Unit tests for {@link PublicationResource}.
 */
public class PublicationResourceTest {
	
	private static final URI BASE_URI = URI.create("EPUB/package.opf");
	
	private PublicationResourceBuilder newBuilder(String location) {
		PublicationResourceBuilderFactory factory = Epub.createResourceBuilderFactory(BASE_URI);
		return factory.newBuilder(location);
	}
	
	/* getLocation() */
	
	@Test
	public void getLocation_shouldReturnLocalLocation() {
		// local location
		PublicationResource resource = newBuilder("chapter1.xhtml").build();
		assertThat(resource.getLocation()).isEqualTo(URI.create("EPUB/chapter1.xhtml"));
	}

	@Test
	public void getLocation_shouldReturnRemoteLocation() {
		String remoteLocation = "http://example.org/chapter1.xhtml";
		PublicationResource resource = newBuilder(remoteLocation).build();
		assertThat(resource.getLocation()).isEqualTo(URI.create(remoteLocation));
	}
	
	/* isLocal() */
	
	@Test
	public void isLocal_shouldReturnTrueForLocalResource() {
		// local location
		PublicationResource resource = newBuilder("chapter1.xhtml").build();
		assertThat(resource.isLocal()).isTrue();
	}

	@Test
	public void isLocal_shouldReturnFalseForRemoteResource() {
		// remote location
		PublicationResource resource = newBuilder("http://example.org/chapter1.xhtml").build();
		assertThat(resource.isLocal()).isFalse();
	}
	
	/* isRemote() */

	@Test
	public void isRemote_shouldReturnTrueForRemoteResource() {
		// remote location
		PublicationResource resource = newBuilder("http://example.org/chapter1.xhtml").build();
		assertThat(resource.isRemote()).isTrue();
	}

	@Test
	public void isRemote_shouldReturnFalseForLocalResource() {
		// local location
		PublicationResource resource = newBuilder("chapter1.xhtml").build();
		assertThat(resource.isRemote()).isFalse();
	}
	
	/* getMediaType */
	
	@Test
	public void getMediaType_shouldReturnCoreMediaType() {
		PublicationResource resource = newBuilder("chapter1.xhtml")
				.ofType(CoreMediaType.APPLICATION_XHTML_XML)
				.build();
		assertThat(resource.getMediaType()).isEqualTo(CoreMediaType.APPLICATION_XHTML_XML);
	}
	
	@Test
	public void getMediaType_shouldReturnForeignMediaType() {
		PublicationResource resource = newBuilder("chapter1.pdf")
				.ofType("application/pdf")
				.build();
		MediaType mediaType = resource.getMediaType();
		assertThat(mediaType.getType()).isEqualTo("application");
		assertThat(mediaType.getSubtype()).isEqualTo("pdf");
	}

	/* isNative() */

	@Test
	public void isNative_shouldReturnTrueForCoreMediaTypeResource() {
		PublicationResource resource = newBuilder("chapter1.xhtml")
				.ofType(CoreMediaType.APPLICATION_XHTML_XML)
				.build();
		assertThat(resource.isNative()).isTrue();
	}

	@Test
	public void isNative_shouldReturnFalseForForeignResource() {
		PublicationResource resource = newBuilder("chapter1.pdf")
				.ofType("application/pdf")
				.build();
		assertThat(resource.isNative()).isFalse();
	}
	
	/* isForeign() */

	@Test
	public void isForeign_shouldReturnTrueForForeignResource() {
		PublicationResource resource = newBuilder("chapter1.pdf")
				.ofType("application/pdf")
				.build();
		assertThat(resource.isForeign()).isTrue();
	}
	
	@Test
	public void isForeign_shouldReturnFalseForCoreMediaTypeResource() {
		PublicationResource resource = newBuilder("chapter1.xhtml")
				.ofType(CoreMediaType.APPLICATION_XHTML_XML)
				.build();
		assertThat(resource.isForeign()).isFalse();
	}
}
