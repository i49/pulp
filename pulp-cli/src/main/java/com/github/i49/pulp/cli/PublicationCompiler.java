/* 
 * Copyright 2017 The Pulp Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.github.i49.pulp.api.Manifest;
import com.github.i49.pulp.api.Metadata;
import com.github.i49.pulp.api.Publication;
import com.github.i49.pulp.api.PublicationResource;
import com.github.i49.pulp.api.PublicationResourceBuilderFactory;
import com.github.i49.pulp.api.Rendition;
import com.github.i49.pulp.api.Spine;

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
		Rendition rendition = publication.addRendition(null);
		buildRendition(rendition);
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
		PublicationResourceBuilderFactory factory = Epub.createResourceBuilderFactory(rendition.getLocation());
		Manifest manifest = rendition.getManifest();
	
		Files.walk(this.sourceDir).filter(Files::isRegularFile).forEach(path->{
			URI uri = path.toUri();
			String relativePath = this.baseURI.relativize(uri).toString();
			if (shouldIgnore(relativePath)) {
				return;
			}
			PublicationResource r = factory.newBuilder(relativePath).source(uri).build();
			Manifest.Item item = manifest.add(r);
			if (checkContentDocument(relativePath)) {
				documents.add(relativePath);
			} else if (checkCoverImage(relativePath)){
				item.asCoverImage();
			}
		});
		
		sortDocuments(documents);
		
		Spine spine = rendition.getSpine();
		for (String document: documents) {
			spine.append(manifest.get(document));
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
