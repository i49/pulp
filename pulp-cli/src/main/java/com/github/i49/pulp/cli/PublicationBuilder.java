package com.github.i49.pulp.cli;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
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

/**
 * A builder to build a publication.
 */
class PublicationBuilder {

	private final Path sourceDir;
	private final URI baseURI;
	private Order order = Order.ASCENDING;
	private Publication publication;
	private final PublicationResourceFactory factory;

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
		this.publication = Epub.createPublication();
		loadMetadata(this.publication.getMetadata());
		addResources();
		sortContentDocuments(this.order);
		return publication;
	}

	private void loadMetadata(Metadata metadata) throws IOException {
		Path path = this.sourceDir.resolve(METADATA_FILENAME);
		if (Files.exists(path)) {
			MetadataLoader loader = MetadataLoader.from(path);
			loader.loadTo(metadata);
		}
	}
	
	private void addResources() throws IOException {
		Files.walk(this.sourceDir).filter(Files::isRegularFile).forEach(path->{
			addResource(path.toUri());
		});
	}
	
	private void addResource(URI location) {
		String name = this.baseURI.relativize(location).toString();
		PublicationResource resource = factory.createResource(name, location);
		if (resource == null) {
			return;
		}
		this.publication.addResource(resource);
		CoreMediaType mediaType = resource.getMediaType();
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			resource.setPrimary(true);
			this.publication.getContentList().add(resource.getName());
		} else if (mediaType.getType().equals("image")) {
			Matcher m = COVER_IMAGE_PATTERN.matcher(name);
			if (m.matches()) {
				this.publication.setCoverImage(name);
			}
		}
	}
	
	protected void sortContentDocuments(Order order) {
		List<String> documents = this.publication.getContentList();
		if (order == Order.ASCENDING) {
			Collections.sort(documents);
		} else {
			Collections.sort(documents, Collections.reverseOrder());
		}
	}
}
