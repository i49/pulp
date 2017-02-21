package com.github.i49.pulp.core;

import java.io.InputStream;

interface Content {

	InputStream openStream() throws Exception;
}
