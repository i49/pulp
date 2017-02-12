package com.github.i49.pulp.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RenditionTest {
	
	private static final Path BASE_PATH = Paths.get("target", "test-classes", "unpacked");
	
	public static class RenditionBuildTest {
		
		private Publication publication;
		private Rendition rendition;
		
		@Before
		public void setUp() {
			this.publication = Epub.createPublication();
			this.rendition = this.publication.addRendition(null);
		}
		
		@Test
		public void testBuild() throws IOException {
			
			Path samplePath = getSamplePath("basic");
			
			Rendition r = this.rendition;
			PublicationResourceBuilderFactory f = rendition.getResourceBuilderFactory();
			r.addResource(f.getBuilder("chapter01.xhtml").content(samplePath.resolve("chapter01.xhtml")).build());
			r.addResource(f.getBuilder("chapter02.xhtml").content(samplePath.resolve("chapter02.xhtml")).build());
			r.addResource(f.getBuilder("images/figure01.png").content(samplePath.resolve("images/figure01.png")).build());
			r.addResource(f.getBuilder("css/stylesheet.css").content(samplePath.resolve("css/stylesheet.css")).build());
			r.addResource(f.getBuilder("cover.png").content(samplePath.resolve("cover.png")).build()).asCoverImage();
			
			List<Rendition.Page> pages = r.getPages();
			pages.add(r.createPage("chapter01.xhtml"));
			pages.add(r.createPage("chapter02.xhtml"));
			
			Path outputPath = Paths.get("target", "basic.zip");
			try (PublicationWriter writer = Epub.createWriter(Files.newOutputStream(outputPath))) {
				writer.write(publication);
			}
		}
	}
	
	private static Path getSamplePath(String name) {
		return BASE_PATH.resolve(name);
	}
}
