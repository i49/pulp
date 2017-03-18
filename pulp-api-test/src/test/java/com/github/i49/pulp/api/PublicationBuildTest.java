package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class PublicationBuildTest {
	
	private static final Path BASE_PATH = Paths.get("target", "test-classes", "unpacked");
	
	@Test
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
