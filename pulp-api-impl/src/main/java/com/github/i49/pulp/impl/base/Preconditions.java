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

import java.util.regex.Pattern;

public final class Preconditions {
	
	private static final Pattern BLANK_PATTERN = Pattern.compile("\\s*");

	/**
	 * Checks if the specified argument is {@code null}.
	 * 
	 * @param object the argument.
	 * @param name the name of the parameter.
	 * @throws IllegalArgumentException if the {@code object} is {@code null}.
	 */
	public static <T> void checkNotNull(T object, String name) {
		if (object == null) {
			throw new IllegalArgumentException("\"" + name + "\" must not be null");
		}
	}
	
	/**
	 * Checks if the specified array of arguments contains {@code null}.
	 * 
	 * @param array the array of arguments.
	 * @param name the name of the parameter.
	 * @throws IllegalArgumentException if the {@code array} contained {@code null}.
	 */
	public static <T> void checkElementNotNull(T[] array, String name) {
		for (T e: array) {
			if (e == null) {
				throw new IllegalArgumentException("\"" + name + "\" must not contain null");
			}
		}
	}
	
	/**
	 * Checks if the specified string is {@code null} or contains white spaces only.
	 * 
	 * @param value the argument of string type.
	 * @param name the name of the parameter.
	 * @throws IllegalArgumentException if the {@code value} is {@code null} or blank.
	 */
	public static void checkNotBlank(String value, String name) {
		checkNotNull(value, name);
		if (BLANK_PATTERN.matcher(value).matches()) {
			throw new IllegalArgumentException("\"" + name + "\" must not be blank");
		}
	}

	private Preconditions() {
	}
}
