package com.github.i49.pulp.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class SampleReaderTest {

	private static Path basePath;
	
	@BeforeClass
	public static void setUpOnce() {
		String samplesDir = System.getProperty("epub3.samples.dir");
		Path samplesPath = Paths.get(samplesDir);
		basePath = samplesPath.resolve("30");
	}

	@Test
	public void accessible_epub_3() {
		Publication p = read("accessible_epub_3.epub");
		assertThat(p, is(notNullValue()));
	}
	
	protected Publication read(String filename) {
		Path filePath = basePath.resolve(filename);
		PublicationReader reader = Epub.createReader(filePath);
		return reader.read();
	}
}
