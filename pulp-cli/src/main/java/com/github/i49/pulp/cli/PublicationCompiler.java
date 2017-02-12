package com.github.i49.pulp.cli;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.i49.pulp.api.Epub;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.Rendition;

/**
 * A class to compile a publication.
 */
class PublicationCompiler {

	private final Path sourceDir;
	private final URI baseURI;
	private Order order = Order.ASCENDING;
	
	private static final String METADATA_FILENAME = "metadata.yaml";
	
	private static final Pattern COVER_IMAGE_PATTERN = Pattern.compile("cover.(png|gif|jpg|jpeg)");
	
	public PublicationCompiler(Path sourceDir) {
		this.sourceDir = sourceDir;
		this.baseURI = sourceDir.toUri();
	}
	
	public void setDocumentOrder(Order order) {
		this.order = order;
	}
	
	public Publication compile() throws IOException {
		Publication publication = Epub.createPublication();
		buildRendition(publication.addRendition(null));
		return publication;
	}
	
	private void buildRendition(Rendition rendition) {
		try {
			loadMetadata(rendition.getMetadata());
			addResourcesToRendition(rendition);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private void loadMetadata(Metadata metadata) throws IOException {
		Path path = this.sourceDir.resolve(METADATA_FILENAME);
		if (Files.exists(path)) {
			MetadataLoader loader = MetadataLoader.from(path);
			loader.loadTo(metadata);
		}
	}
	
	private void addResourcesToRendition(Rendition rendition) throws IOException {
		List<String> documents = new ArrayList<>();
		PublicationResourceBuilderFactory f = rendition.getResourceBuilderFactory();
	
		Files.walk(this.sourceDir).filter(Files::isRegularFile).forEach(path->{
			URI uri = path.toUri();
			String relativePath = this.baseURI.relativize(uri).toString();
			if (shouldIgnore(relativePath)) {
				return;
			}
			PublicationResource r = f.get(relativePath).from(uri).build();
			Rendition.Item item = rendition.require(r);
			if (checkContentDocument(relativePath)) {
				documents.add(relativePath);
			} else if (checkCoverImage(relativePath)){
				item.asCoverImage();
			}
		});
		
		sortDocuments(documents);
		
		for (String document: documents) {
			rendition.getPageList().add(rendition.page(document));
		}
	}
	
	private static boolean shouldIgnore(String name) {
		return name.endsWith("yaml");
	}
	
	private static boolean checkContentDocument(String name) {
		return name.endsWith(".xhtml");
	}
	
	private static boolean checkCoverImage(String name) {
		Matcher m = COVER_IMAGE_PATTERN.matcher(name);
		return m.matches();
	}
	
	private void sortDocuments(List<String> documents) {
		if (order == Order.ASCENDING) {
			Collections.sort(documents);
		} else {
			Collections.sort(documents, Collections.reverseOrder());
		}
	}
}
