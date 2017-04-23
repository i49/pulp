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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.github.i49.pulp.api.metadata.DateTypeProperty;

/**
 * A skeletal implementation of {@link DateTypeProperty}.
 */
abstract class AbstractDateTypeProperty extends AbstractProperty implements DateTypeProperty {

	private static final DateTimeFormatter ISO8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	private final OffsetDateTime dateTime;
	
	protected AbstractDateTypeProperty(OffsetDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String getValue() {
		OffsetDateTime utc = OffsetDateTime.ofInstant(getDateTime().toInstant(), ZoneOffset.UTC);
		return utc.format(ISO8601_FORMATTER);
	}
	
	@Override
	public OffsetDateTime getDateTime() {
		return dateTime;
	}
}