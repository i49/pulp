package com.github.i49.pulp.cli;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.i49.pulp.api.CoreMediaType;
import com.github.i49.pulp.api.Epub;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceFactory;
import com.github.i49.pulp.api.Rendition;

/**
 * A builder to build a publication.
 */
class PublicationBuilder {

	private final Path sourceDir;
	private final URI baseURI;
	private Order order = Order.ASCENDING;
	
	private final PublicationResourceFactory factory;
	private final List<String> contents = new ArrayList<>();
	
	private static final String METADATA_FILENAME = "metadata.yaml";
	private static final Pattern COVER_IMAGE_PATTERN = Pattern.compile("cover.(png|gif|jpg|jpeg)");
	
	public PublicationBuilder(Path sourceDir) {
		this.sourceDir = sourceDir;
		this.baseURI = sourceDir.toUri();
		this.factory = Epub.createResourceFactory();
	}
	
	public void setDocumentOrder(Order order) {
		this.order = order;
	}
	
	public Publication build() throws IOException {
		Publication publication = Epub.createPublication();
		Rendition rendition = publication.getDefaultRendition();
		loadMetadata(rendition.getMetadata());
		addResources(rendition);
		sortContentDocuments(rendition, this.order);
		return publication;
	}

	private void loadMetadata(Metadata metadata) throws IOException {
		Path path = this.sourceDir.resolve(METADATA_FILENAME);
		if (Files.exists(path)) {
			MetadataLoader loader = MetadataLoader.from(path);
			loader.loadTo(metadata);
		}
	}
	
	private void addResources(Rendition rendition) throws IOException {
		Files.walk(this.sourceDir).filter(Files::isRegularFile).forEach(path->{
			addResource(rendition, path.toUri());
		});
	}
	
	private void addResource(Rendition rendition, URI location) {
		String name = this.baseURI.relativize(location).toString();
		PublicationResource resource = factory.createResource(name, location);
		if (resource == null) {
			return;
		}
		rendition.addResource(resource);
		CoreMediaType mediaType = resource.getMediaType();
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			contents.add(name);
		} else if (mediaType.getType().equals("image")) {
			Matcher m = COVER_IMAGE_PATTERN.matcher(name);
			if (m.matches()) {
				rendition.setCoverImage(name);
			}
		}
	}
	
	protected void sortContentDocuments(Rendition rendition, Order order) {
		if (order == Order.ASCENDING) {
			Collections.sort(this.contents);
		} else {
			Collections.sort(this.contents, Collections.reverseOrder());
		}
		for (String name: this.contents) {
			PublicationResource resource = rendition.getResourceByName(name);
			rendition.getSpine().add(resource);
		}
	}
}
