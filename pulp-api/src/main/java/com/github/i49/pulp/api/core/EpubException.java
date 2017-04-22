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

/**
 * The exception thrown by Java API for EPUB processing.
 */
public class EpubException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs this exception with the specified detail message. 
	 * @param message the detail message that can be retrieved by calling {@link #getMessage()}. cannot be {@code null}.
	 */
	public EpubException(String message) {
		super(message);
	}
	
	/**
	 * Constructs this exception with the specified detail message and cause. 
	 * @param message the detail message that can be retrieved by calling {@link #getMessage()}. cannot be {@code null}.
	 * @param cause the real cause of this exception that can be retrieved by calling {@link #getCause()}. 
	 */
	public EpubException(String message, Throwable cause) {
		super(message, cause);
	}
}
