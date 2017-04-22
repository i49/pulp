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

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.pulp.api.core.Epub;
import com.github.i49.pulp.api.core.Manifest;
import com.github.i49.pulp.api.core.Publication;
import com.github.i49.pulp.api.core.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.core.PublicationWriter;
import com.github.i49.pulp.api.core.PublicationWriterFactory;
import com.github.i49.pulp.api.core.Rendition;
import com.github.i49.pulp.api.core.Spine;

/**
 * Unit tests for {@link PublicationWriter}.
 */
public class PublicationWriterTest {

	private PublicationWriterFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createWriterFactory();
	}
	
	public static Path sourcePath(String name) {
		return EpubPaths.get(name);
	}
	
	public static Path outputPath(String filename) {
		return Paths.get("target", filename);
	}
	
	@Test
	public void write_shouldWritePublicationBuilt() {
		
		Path sourcePath = sourcePath("valid-single-rendition/EPUB"); 

		Publication publication = Epub.createPublication();
		Rendition rendition = publication.addRendition();
		
		PublicationResourceBuilderFactory f = Epub.createResourceBuilderFactory(rendition.getLocation());
		f.setSourcePath(sourcePath);
		
		Manifest m = rendition.getManifest();
		m.add(f.newBuilder("chapter1.xhtml").build());
		m.add(f.newBuilder("chapter2.xhtml").build());
		m.add(f.newBuilder("nav.xhtml").build()).asNavigation();
		m.add(f.newBuilder("images/figure1.jpg").build());
		m.add(f.newBuilder("css/stylesheet.css").build());
		m.add(f.newBuilder("cover.png").build()).asCoverImage();
		
		Spine spine = rendition.getSpine();
		spine.append(m.get("chapter1.xhtml"));
		spine.append(m.get("chapter2.xhtml"));
	
		Path outputPath = outputPath("valid-single-rendition.epub");
		try (PublicationWriter writer = factory.createWriter(outputPath)) {
			writer.write(publication);
		}

		assertThat(publication.getDefaultRendition()).isSameAs(rendition);
	}
}
