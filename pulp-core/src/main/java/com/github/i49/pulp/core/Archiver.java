package com.github.i49.pulp.core;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * General archiver interface.
 */
interface Archiver extends Closeable {

	OutputStream append(String name) throws IOException;
	
	void appendRaw(String name, byte[] contents) throws IOException;
}
