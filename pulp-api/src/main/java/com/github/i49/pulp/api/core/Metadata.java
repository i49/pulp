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

package com.github.i49.pulp.api.core;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

/**
 * Metadata of a publication.
 */
public interface Metadata {

	String getIdentifier();

	void setIdentifier(String identifier);

	List<String> getTitles();
	
	List<Locale> getLanguages();
	
	List<String> getCreators();
	
	List<String> getPublishers();
	
	OffsetDateTime getDate();
	
	void setDate(OffsetDateTime date);
	
	OffsetDateTime getLastModified();
	
	void setLastModified(OffsetDateTime lastModified);
}
