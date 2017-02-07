package com.github.i49.pulp.api;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class PublicationBuilderTest {
	
	private static final Path dir = Paths.get("target", "test-classes", "samples", "helloworld");

	@Test
	public void test() {
		
		Publication pub = Epub.createPublication(p->{
			p.addRendition(r->{
				r.addResource("helloworld.xhtml", dir.resolve("helloworld.xhtml"));
				r.addPage("helloworld.xhtml");
			});
		});
		
		assertThat(pub, is(notNullValue()));
	}
}
