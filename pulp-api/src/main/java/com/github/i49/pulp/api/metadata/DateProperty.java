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

package com.github.i49.pulp.api.metadata;

import java.time.OffsetDateTime;

/**
 * A metadata property that has a value of date and time.
 */
public interface DateProperty extends TypedProperty<OffsetDateTime> {

	/**
	 * Returns the string representation of this date
	 * in the format defined by ISO8601. 
	 * The time is converted into UTC.
	 * 
	 * <p>Example: "2000-01-01T00:00:00Z"</p>
	 * 
	 * @return the string representation of this date.
	 * @see <a href="https://www.iso.org/standard/40874.html">ISO 8601:2004</a>
	 */
	@Override
	String getValueAsString();

	/**
	 * {@inheritDoc}
	 */
	@Override
	DateProperty setValue(OffsetDateTime value);
}
