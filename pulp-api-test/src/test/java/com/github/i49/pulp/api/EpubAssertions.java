package com.github.i49.pulp.api;

/**
 * Custom assertions.
 */
public final class EpubAssertions {

	/**
	 * Provides assertion on an instance of {@link Manifest}. 
	 * @param actual the actual instance of {@link Manifest}. 
	 * @return created assertion.
	 */
	public static ManifestAssert assertThat(Manifest actual) {
		return ManifestAssert.assertThat(actual);
	}

	/**
	 * Provides assertion on an instance of {@link Spine}. 
	 * @param actual the actual instance of {@link Spine}. 
	 * @return created assertion.
	 */
	public static SpineAssert assertThat(Spine actual) {
		return SpineAssert.assertThat(actual);
	}
	
	private EpubAssertions() {
	}
}
