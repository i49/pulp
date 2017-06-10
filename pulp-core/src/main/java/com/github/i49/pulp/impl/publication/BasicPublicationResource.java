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

package com.github.i49.pulp.impl.publication;

import static com.github.i49.pulp.impl.base.Preconditions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.EnumSet;

import com.github.i49.pulp.api.publication.ContentSource;
import com.github.i49.pulp.api.publication.CoreMediaType;
import com.github.i49.pulp.api.publication.MediaType;
import com.github.i49.pulp.api.publication.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
class BasicPublicationResource implements PublicationResource {

	private static final EnumSet<CoreMediaType> CORE_MEDIA_TYPES = EnumSet.allOf(CoreMediaType.class);
	private static final int BUFFER_SIZE = 64 * 1024;

	private final PublicationResourceLocation location;
	private final MediaType mediaType;
	private ContentSource source;
	
	public BasicPublicationResource(PublicationResourceLocation location, MediaType mediaType) {
		assert(location != null && mediaType != null);
		this.location = location;
		this.mediaType = mediaType;
	}

	@Override
	public URI getLocation() {
		return location.toURI();
	}
	
	@Override
	public boolean isLocal() {
		return location.isLocal();
	}
	
	@Override
	public boolean isRemote() {
		return !isLocal();
	}
	
	@Override
	public MediaType getMediaType() {
		return mediaType;
	}
	
	@Override
	public boolean isNative() {
		return CORE_MEDIA_TYPES.contains(getMediaType());
	}
	
	@Override
	public boolean isForeign() {
		return !isNative();
	}
	
	@Override
	public ContentSource getContentSource() {
		return source;
	}
	
	@Override
	public void setContentSource(ContentSource source) {
		checkNotNull(source, "source");
		this.source = source;
	}
	
	@Override
	public InputStream openContent() throws IOException {
		if (this.source == null) {
			throw new IllegalStateException();
		}
		return this.source.openSource(getLocation());
	}
	
	@Override
	public byte[] getContent() throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = openContent(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			return out.toByteArray();
		}
	}

	@Override
	public String toString() {
		return getLocation().toString();
	}
}
