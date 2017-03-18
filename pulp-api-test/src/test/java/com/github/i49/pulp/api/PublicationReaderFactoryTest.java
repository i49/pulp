package com.github.i49.pulp.api;

import static org.assertj.core.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class PublicationReaderFactoryTest {
	
	private PublicationReaderFactory factory;
	
	@Before
	public void setUp() {
		factory = Epub.createReaderFactory();
	}
	
	@Test
	public void createReader_shoudThrowExceptionIfPathNotExist() {
		Path path = Paths.get("nonexistent.epub");
		assertThatThrownBy(()->{
			factory.createReader(path);
		}).isInstanceOf(EpubException.class).hasMessageContaining("nonexistent.epub");
	}
}
