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

package com.github.i49.pulp.impl.metadata;

import java.util.Optional;

import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;
import com.github.i49.pulp.api.vocabularies.dc.Identifier;
import com.github.i49.pulp.api.vocabularies.dcterms.Modified;

/**
 * The default implementation of {@link ReleaseIdentifier}.
 */
public class DefaultReleaseIdentifier implements ReleaseIdentifier {

	private final Metadata metadata;
	
	public DefaultReleaseIdentifier(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public Optional<Identifier> getUniqueIdentifier() {
		return metadata.find().identifier().stream().filter(Identifier::isPrimary).findFirst();
	}

	@Override
	public Optional<Modified> getLastModificationDate() {
		return metadata.find().modified().stream().findFirst();
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		getUniqueIdentifier().ifPresent(identifier->{
			b.append(identifier.getValue());
		});
		b.append("@");
		getLastModificationDate().ifPresent(modified->{
			b.append(modified.getValue());
		});
		return b.toString();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		DefaultReleaseIdentifier other = (DefaultReleaseIdentifier)object;
		return toString().equals(other.toString());
	}
}
