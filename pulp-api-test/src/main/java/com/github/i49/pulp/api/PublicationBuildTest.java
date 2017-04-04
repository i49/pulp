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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class PublicationBuildTest {
	
	private static final Path BASE_PATH = Paths.get("target", "test-classes", "unpacked");
	
	public void buildBasic() throws IOException {
		
		Path dir = pathToDir("basic");
		
		Publication publication = Epub.createPublication();
		Rendition rendition = publication.addRendition();
		PublicationResourceBuilderFactory f = Epub.createResourceBuilderFactory(rendition.getLocation());
		Manifest m = rendition.getManifest();
		m.add(f.newBuilder("chapter01.xhtml").sourceDir(dir).build());
		m.add(f.newBuilder("chapter02.xhtml").sourceDir(dir).build());
		m.add(f.newBuilder("images/figure01.png").sourceDir(dir).build());
		m.add(f.newBuilder("css/stylesheet.css").sourceDir(dir).build());
		m.add(f.newBuilder("cover.png").sourceDir(dir).build()).asCoverImage();
		
		Spine spine = rendition.getSpine();
		spine.append(m.get("chapter01.xhtml"));
		spine.append(m.get("chapter02.xhtml"));
		
		assertThat(m.getNumberOfItems()).isEqualTo(5);
		assertThat(spine.getNumberOfPages()).isEqualTo(2);
		
		Path outputPath = Paths.get("target", "basic.zip");
		try (PublicationWriter writer = Epub.createWriter(outputPath)) {
			writer.write(publication);
		}
		
		assertThat(publication.getDefaultRendition()).isEqualTo(rendition);
	}
	
	private static Path pathToDir(String name) {
		return BASE_PATH.resolve(name);
	}
}
