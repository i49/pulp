package com.github.i49.pulp.api;

import java.util.function.Consumer;

/**
 * A interface to build a publication.
 */
public interface PublicationBuilder {

	PublicationBuilder addRendition(Consumer<RenditionBuilder> r);
}
