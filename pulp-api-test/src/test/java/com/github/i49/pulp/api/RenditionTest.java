package com.github.i49.pulp.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class RenditionTest {
	
	private static final Path BASE_PATH = Paths.get("target", "test-classes", "unpacked");
	
	public static class RenditionBuildTest {
		
		@Test
		public void testBuild() throws IOException {
			
			Path dir = pathToDir("basic");
			
			Publication publication = Epub.createPublication();
			Rendition rendition = publication.addRendition();
			PublicationResourceRegistry r = rendition.getResourceRegistry();
			Manifest m = rendition.getManifest();
			m.add(r.builder("chapter01.xhtml").sourceDir(dir).build());
			m.add(r.builder("chapter02.xhtml").sourceDir(dir).build());
			m.add(r.builder("images/figure01.png").sourceDir(dir).build());
			m.add(r.builder("css/stylesheet.css").sourceDir(dir).build());
			m.add(r.builder("cover.png").sourceDir(dir).build()).asCoverImage();
			
			Spine spine = rendition.getSpine();
			spine.append(m.get("chapter01.xhtml"));
			spine.append(m.get("chapter02.xhtml"));
			
			assertThat(m.getNumberOfItems(), equalTo(5));
			assertThat(spine.getNumberOfPages(), equalTo(2));
			
			Path outputPath = Paths.get("target", "basic.zip");
			try (PublicationWriter writer = Epub.createWriter(Files.newOutputStream(outputPath))) {
				writer.write(publication);
			}
			
			assertThat(publication.getDefaultRendition(), is(rendition));
		}
	}
	
	private static Path pathToDir(String name) {
		return BASE_PATH.resolve(name);
	}
}
