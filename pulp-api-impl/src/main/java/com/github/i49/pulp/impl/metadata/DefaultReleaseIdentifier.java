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

import com.github.i49.pulp.api.metadata.BasicTerm;
import com.github.i49.pulp.api.metadata.Identifier;
import com.github.i49.pulp.api.metadata.Metadata;
import com.github.i49.pulp.api.metadata.Modified;
import com.github.i49.pulp.api.metadata.ReleaseIdentifier;

/**
 * The default implementation of {@link ReleaseIdentifier}.
 */
public class DefaultReleaseIdentifier implements ReleaseIdentifier {

	private final Metadata metadata;
	
	public DefaultReleaseIdentifier(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public Identifier getUniqueIdentifier() {
		return (Identifier)metadata.get(BasicTerm.IDENTIFIER);
	}

	@Override
	public Modified getLastModificationDate() {
		return (Modified)metadata.get(BasicTerm.MODIFIED);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(getUniqueIdentifier().getValue());
		b.append("@");
		b.append(getLastModificationDate().getValue());
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
