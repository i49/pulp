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

package com.github.i49.pulp.impl.base;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * A text parser to produce {@link OffsetDateTime} objects.
 */
public interface DateTimeParser {

	OffsetDateTime parse(String text) throws DateTimeParseException;
	
	static final DateTimeParser ISO_8601 = new IsoDateTimeParser();
}

class IsoDateTimeParser implements DateTimeParser {
	
	private static final Pattern YEAR = Pattern.compile("\\d{4}");
	private static final Pattern LOCAL_DATE = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

	@Override
	public OffsetDateTime parse(String text) {
		if (YEAR.matcher(text).matches()) {
			return parseYear(text);
		}
		if (LOCAL_DATE.matcher(text).matches()) {
			return parseLocalDate(text);
		}
		return parseOffsetDateTime(text);
	}
	
	private OffsetDateTime parseYear(String text) {
		int year = Integer.parseInt(text);
		return OffsetDateTime.of(year, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
	}
	
	private OffsetDateTime parseLocalDate(String text) {
		LocalDate date = LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalTime time = LocalTime.of(0, 0);
		return OffsetDateTime.of(date, time, ZoneOffset.UTC);
	}
	
	private OffsetDateTime parseOffsetDateTime(String text) {
		return OffsetDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
}
