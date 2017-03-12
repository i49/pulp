package com.github.i49.pulp.api;

import org.assertj.core.api.AbstractAssert;

import com.github.i49.pulp.api.Spine.Page;

public class SpineAssert extends AbstractAssert<SpineAssert, Spine> {

	public static SpineAssert assertThat(Spine actual) {
		return new SpineAssert(actual);
	}
	
	private SpineAssert(Spine actual) {
		super(actual, SpineAssert.class);
	}
	
	public SpineAssert hasPages(int expected) {
		isNotNull();
		if (actual.getNumberOfPages() != expected) {
			failWithMessage("Expected nubmer of pages to be <%d>, but was <%d>", expected, actual.getNumberOfPages());
		}
		return this;
	}
	
	public SpineAssert linearExcept(int... indices) {
		isNotNull();

		int next = 0;
		for (int i = 0; i < actual.getNumberOfPages(); i++) {
			Page page = actual.get(i);
			if (next < indices.length && i == indices[next]) {
				if (page.isLinear()) {
					failWithMessage("Page at %d must be non-linear", i);
				}
				next++;
			} else {
				if (!page.isLinear()) {
					failWithMessage("Page at %d must be linear", i);
				}
			}
		}
		
		return this;
	}
}
