package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class PublicationWriterFactoryTest {

	private PublicationWriterFactory factory;

	@Before
	public void setUp() {
		factory = Epub.createWriterFactory();
	}
	
	@Test
	public void createWriter_shouldCreateWriterWithPath() {
		Path path = Paths.get("target", "empty.epub");
		try (PublicationWriter writer = factory.createWriter(path)) {
			assertThat(writer).isNotNull();
		}
	}

	@Test
	public void createWriter_shouldThrowExceptionIfPathIsDirectory() {
		Path path = Paths.get("target");
		assertThatThrownBy(()->{
			factory.createWriter(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("target");
	}
}
