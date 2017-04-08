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

import static com.github.i49.pulp.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link PublicationReader}.
 */
public class PublicationReaderTest {

	private PublicationReaderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createReaderFactory();
	}
	
	private Path pathTo(String filename) {
		return EpubPaths.get(filename);
	}
	
	/* read() */
	
	@Test
	public void read_shouldReadPublicationOfSingleRendition() {
		Path path = pathTo("valid-single-rendition.epub");
		PublicationReader reader = factory.createReader(path);
		Publication publication = reader.read();
		
		assertThat(publication).isNotNull();
		assertThat(publication.getNumberOfRenditions()).isEqualTo(1);
		
		Rendition rendition = publication.getDefaultRendition();
		assertThat(rendition.getManifest().getNumberOfItems()).isEqualTo(6);
		assertThat(rendition.getSpine().getNumberOfPages()).isEqualTo(2);
	}

	@Test
	public void read_shouldReadPublicationOfMultipleRenditions() {
		Path path = pathTo("valid-multiple-renditions.epub");
		PublicationReader reader = factory.createReader(path);
		Publication publication = reader.read();
		
		assertThat(publication).isNotNull();
		assertThat(publication.getNumberOfRenditions()).isEqualTo(2);
		
		Iterator<Rendition> it = publication.iterator();
		Rendition first = it.next();
		assertThat(first.getManifest().getNumberOfItems()).isEqualTo(3);
		assertThat(first.getSpine().getNumberOfPages()).isEqualTo(2);
		
		Rendition second = it.next();
		assertThat(second.getManifest().getNumberOfItems()).isEqualTo(4);
		assertThat(second.getSpine().getNumberOfPages()).isEqualTo(3);
	}
	
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
		Path path = pathTo("container-unrecognized.epub");
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
	
	@Test
	public void read_shouldThrowExceptionIfPackageDocumentIsMissing() {
		Path path = pathTo("package-missing.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("EPUB/nonexistent.opf")
			.hasContainerPath(path);
	}

	@Test
	public void read_shouldThrowExceptionIfPackageDocumentIsEmpty() {
		Path path = pathTo("package-empty.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("EPUB/empty.opf")
			.hasContainerPath(path);
	}

	@Test
	public void read_shouldThrowExceptionIfPackageDocumentHasUnrecognizedRootElement() {
		Path path = pathTo("package-unrecognized.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("EPUB/package.opf")
			.hasContainerPath(path);
	}

	@Test
	public void read_shouldThrowExceptionIfPackageDocumentHasUnsupportedVersion() {
		Path path = pathTo("package-unsupported.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown).isInstanceOf(EpubParsingException.class);
		assertThat((EpubParsingException)thrown)
			.hasLocation("EPUB/package.opf")
			.hasContainerPath(path);
	}

	@Test
	public void read_shouldThrowExceptionIfPackageItemIsMissing() {
		Path path = pathTo("package-item-missing.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown)
			.isInstanceOf(EpubParsingException.class)
			.hasMessageContaining("itemMissing");
		assertThat((EpubParsingException)thrown)
			.hasLocation("EPUB/package.opf")
			.hasContainerPath(path);
	}

	@Test
	public void read_shouldThrowExceptionIfPackageResourceIsMissing() {
		Path path = pathTo("package-resource-missing.epub");
		PublicationReader reader = factory.createReader(path);
		Throwable thrown = catchThrowable(()->{
			reader.read();
		});
		assertThat(thrown)
			.isInstanceOf(EpubParsingException.class)
			.hasMessageContaining("missing.png");
		assertThat((EpubParsingException)thrown)
			.hasLocation("EPUB/package.opf")
			.hasContainerPath(path);
	}
}
