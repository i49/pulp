package com.github.i49.pulp.api;

import java.io.IOException;
import java.util.Optional;

import org.assertj.core.api.AbstractAssert;

public class ManifestAssert extends AbstractAssert<ManifestAssert, Manifest> {

	public static ManifestAssert assertThat(Manifest actual) {
		return new ManifestAssert(actual);
	}
	
	private ManifestAssert(Manifest actual) {
		super(actual, ManifestAssert.class);
	}

	public ManifestAssert hasItems(int expected) {
		isNotNull();
		if (actual.getNumberOfItems() != expected) {
			failWithMessage("Expected nubmer of items to be <%d>, but was <%d>", expected, actual.getNumberOfItems());
		}
		return this;
	}
	
	public ManifestAssert hasResource(String location, int length) {
		isNotNull();
		Optional<Manifest.Item> item = actual.find(location);
		if (!item.isPresent()) {
			failWithMessage("Manifest did not contain the resource at <%s>", location);
		}
		PublicationResource resource = item.get().getResource();
		try {
			byte[] content = resource.getContent();
			if (content.length != length) {
				failWithMessage("Expected content length to be <%d>, but was <%d>", length, content.length);
			}
		} catch (IOException e) {
			failWithMessage("Failed to load content of resource at <%s>", location);
		}
		return this;
	}
}
