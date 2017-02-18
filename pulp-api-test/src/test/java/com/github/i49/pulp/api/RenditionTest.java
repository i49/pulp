package com.github.i49.pulp.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class RenditionTest {
	
	private static final Path BASE_PATH = Paths.get("target", "test-classes", "unpacked");
	
	public static class RenditionBuildTest {
		
		@Test
		public void testBuild() throws IOException {
			
			Path dir = pathToDir("basic");
			
			Publication p = Epub.createPublication();
			Rendition r = p.addRendition();
			PublicationResourceRegistry registry = r.getResourceRegistry();
			r.require(registry.builder("chapter01.xhtml").sourceDir(dir).build());
			r.require(registry.builder("chapter02.xhtml").sourceDir(dir).build());
			r.require(registry.builder("images/figure01.png").sourceDir(dir).build());
			r.require(registry.builder("css/stylesheet.css").sourceDir(dir).build());
			r.require(registry.builder("cover.png").sourceDir(dir).build()).asCoverImage();
			
			List<Rendition.Page> pages = r.getPageList();
			pages.add(r.page("chapter01.xhtml"));
			pages.add(r.page("chapter02.xhtml"));
			
			Path outputPath = Paths.get("target", "basic.zip");
			try (PublicationWriter writer = Epub.createWriter(Files.newOutputStream(outputPath))) {
				writer.write(p);
			}
			
			assertThat(p.getDefaultRendition(), is(r));
		}
	}
	
	private static Path pathToDir(String name) {
		return BASE_PATH.resolve(name);
	}
}
